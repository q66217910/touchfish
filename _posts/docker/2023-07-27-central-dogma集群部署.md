---
layout: post
title: central-dogma集群部署
category: docker
tags: [docker]
no-post-nav: true
---

# central-dogma集群部署

[central-dogma](https://line.github.io/centraldogma/)是基于ZooKeeper和Git的开源的高可用版本控制服务.

## 1.central-dogma非集群部署
官方部署文档: https://line.github.io/centraldogma/setup-installation.html

官方部署文档缺少在k8s集群中的部署

## 2.编写集群配置
可参考官方文档: https://line.github.io/centraldogma/setup-configuration.html

关键点为serverId和servers,我们需要根据节点数动态配置.所以此处将其设置为占位符.
```json
{
  "dataDir": "./data",
  "ports": [
    {
      "localAddress": {
        "host": "*",
        "port": 36462
      },
      "protocols": [
        "http"
      ]
    }
  ],
  "tls": null,
  "trustedProxyAddresses": null,
  "clientAddressSources": null,
  "numWorkers": null,
  "maxNumConnections": null,
  "requestTimeoutMillis": null,
  "idleTimeoutMillis": null,
  "maxFrameLength": null,
  "numRepositoryWorkers": 16,
  "maxRemovedRepositoryAgeMillis": null,
  "repositoryCacheSpec": "maximumWeight=134217728,expireAfterAccess=5m",
  "gracefulShutdownTimeout": {
    "quietPeriodMillis": 1000,
    "timeoutMillis": 10000
  },
  "webAppEnabled": true,
  "webAppTitle": "Config Center",
  "mirroringEnabled": null,
  "numMirroringThreads": null,
  "maxNumFilesPerMirror": 20000,
  "maxNumBytesPerMirror": 209715200,
  "replication": {
    "method": "ZOOKEEPER",
    "serverId": ${MACHINE_INDEX},
    "servers": {
        ${SERVERS}
    },
    "secret": "F093C514-367F-4372-B4BC-68055759D609",
    "additionalProperties": {},
    "timeoutMillis": null,
    "numWorkers": null,
    "maxLogCount": null,
    "minLogAgeMillis": null
  },
  "csrfTokenRequiredForThrift": null,
  "accessLogFormat": "common",
  "authentication": {
    "factoryClassName": "com.linecorp.centraldogma.server.auth.shiro.ShiroAuthProviderFactory",
    "administrators": [],
    "caseSensitiveLoginNames": false,
    "sessionCacheSpec": "maximumSize=8192,expireAfterWrite=604800s",
    "sessionTimeoutMillis": 604800000,
    "sessionValidationSchedule": "0 30 */4 ? * *",
    "properties": "./conf/shiro.ini"
  }
}
```

## 3.编写启动替换配置脚本
由于我们需要动态配置serverId和servers所以我们需要编写一个启动时替换的脚本

### 3-1.查看central-dogma启动脚本

查看启动脚本 **/opt/centraldogma/bin/startup**
```shell
cat /opt/centraldogma/bin/startup
```

启动脚本: 

在启动脚本的第一行会执行 **source "$(dirname "$0")/common.sh"** , common.sh的脚本,接下来查看common.sh脚本
```shell
#!/bin/bash -e

# shellcheck source=common.sh
source "$(dirname "$0")/common.sh"
cd "$APP_HOME"

NODETACH=0

while [[ $# -gt 0 ]]
do
  case "$1" in
    -nodetach)
      NODETACH=1
      ;;
    *)
      eecho "Unknown argument: $1"
      eecho "Usage: `basename $0` [-nodetach]"
      exit 10
      ;;
  esac
  shift
done

###以下省略###
```

### 3-2.查看common.sh脚本
执行查看脚本
```shell
cat /opt/centraldogma/bin/common.sh
```
脚本内容:

可以看到在执行common.sh时,脚本会判断是否存在**common.pre.sh**脚本,并执行
```shell
set -e

function eecho() {
  echo "$@" >&2
}

if [[ -f "$(dirname "$0")/common.pre.sh" ]]; then
  # shellcheck source=common.pre.sh
  source "$(dirname "$0")/common.pre.sh"
fi

###以下省略###
```

### 3-3.查看common.pre.sh脚本
执行查看脚本
```shell
cat /opt/centraldogma/bin/common.pre.sh
```

脚本内容:
```shell
APP_NAME=centraldogma
APP_MAIN=com.linecorp.centraldogma.server.Main
```

### 3-4.重写common.pre.sh脚本
可以看出在启动时会执行common.pre.sh脚本,我们可以重写common.pre.sh脚本将我们的动态逻辑加在脚本中

替换逻辑说明:
* 根据环境变量REPLICA_COUNT指定总集群的节点数
* 由于在k8s中使用statefulSet部署,根据POD的编号前面加"1"作为当前节点的serverId
* 替换文件中的占位符内容
```shell
###原逻辑
APP_NAME=centraldogma
APP_MAIN=com.linecorp.centraldogma.server.Main

###替换逻辑
set -x
#有副本数的时候才需要替换
if [ -n "$REPLICA_COUNT" ]
then
  #机器号
  MACHINE_INDEX="${HOSTNAME##*-}"
  #由于机器号是从0开始的,index不能等于0,所以需要在前面加1
  MACHINE_INDEX=1$MACHINE_INDEX
  #配置文件
  config_file="/opt/centraldogma/conf/dogma-replica.json"
  if [ -n "$MACHINE_INDEX" ]
  then
    #替换配置文件中的机器号
    # shellcheck disable=SC2016
    sed -i 's#${MACHINE_INDEX}#'"$MACHINE_INDEX"'#g' "$config_file"
  fi
  server=""
  #循环副本数,设置各个节点的配置
  for ((i=0;i<$REPLICA_COUNT;i++))
  do
    conf="""\"1$i\": {\"host\": \"config-server-$i.config-server-headless\",\"quorumPort\": 36463,\"electionPort\": 36464,\"groupId\": null,\"weight\": null}"""
    #替换配置文件中的副本数
    server=$server","$conf
  done
  server=${server:1}
  # shellcheck disable=SC2016
  sed -i 's#${SERVERS}#'"$server"'#g' "$config_file"
  #替换dogma.json文件
  rm -rf /opt/centraldogma/conf/dogma.json
  mv $config_file /opt/centraldogma/conf/dogma.json
fi
```

## 4.Docker自定义镜像的制作
以上步骤2和步骤3的内容可以使用制作镜像的方式打入到镜像内,在k8s中也可以通过ConfigMap的形式挂载到目录下(推荐)
```Dockerfile
ARG BASE_VERSION=0.61.4
FROM ghcr.io/line/centraldogma:${BASE_VERSION}

ENV CENTRALDOGMA_HOME "/opt/centraldogma"
COPY conf "$CENTRALDOGMA_HOME"/conf/
COPY bin "$CENTRALDOGMA_HOME"/bin/
COPY libs/* "$CENTRALDOGMA_HOME"/lib/

RUN rm -rf /app && ln -s "$CENTRALDOGMA_HOME" /app && ln -s "$CENTRALDOGMA_HOME"/conf "$CENTRALDOGMA_HOME"/config && \
    ln -sf /dev/stdout "$CENTRALDOGMA_HOME/log/centraldogma.stdout" && ln -sf /dev/stderr "$CENTRALDOGMA_HOME/log/centraldogma.stderr"

EXPOSE 36462

ENTRYPOINT ["/opt/centraldogma/bin/startup", "-nodetach"]
```

### 4-1.集群启动报错问题
在最新版本0.61.4中,集群启动会报错缺少依赖snappy-java-1.1.10.3.jar错误

所以此处在制作镜像中将依赖snappy-java-1.1.10.3.jar,添加到**$CENTRALDOGMA_HOME/lib/**下

## 5.K8S中部署

### 5-1.StatefulSet编写

* spec.replicas的节点数需要跟环境变量REPLICA_COUNT数量保持一致
* 需要加增加podManagementPolicy: Parallel,使StatefulSet中的POD同时启动(原因为central-dogma在集群启动过程中会进行zookeeper选举)
若不同时启动,在只启动一个服务,网络不通的情况下,无法选举,服务也无法启动.导致本节点启动失败和后续节点无法启动
* readinessProbe也无法设置,原因同上,在服务启动过程中zookeeper选举没有完成,服务并不健康,若不可读,无法进行选举.

```yaml
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: config-server
  labels:
    app: config-server
spec:
  serviceName: config-server-headless
  replicas: 3
  podManagementPolicy: Parallel
  selector:
    matchLabels:
      app.kubernetes.io/name: config-server
      app.kubernetes.io/instance: config-server
  template:
    metadata:
      labels:
        app.kubernetes.io/name: config-server
        app.kubernetes.io/instance: config-server
    spec:
      containers:
        - name: app
          image: ghcr.io/line/centraldogma:0.61.4
          imagePullPolicy: Always
          ports:
            - name: http
              containerPort: 36462
              protocol: TCP
            - name: quorum
              containerPort: 36463
              protocol: TCP
            - name: election
              containerPort: 36464
              protocol: TCP   
          livenessProbe:
            httpGet:
              path: /monitor/l7check
              port: 36462
            initialDelaySeconds: 120
            timeoutSeconds: 5
            periodSeconds: 10
            failureThreshold: 3
#          readinessProbe:
#            httpGet:
#              path: /monitor/l7check
#              port: 36462
#            initialDelaySeconds: 20
#            timeoutSeconds: 2
#            periodSeconds: 5
#            failureThreshold: 3
          env:
            - name: POD_ID
              valueFrom:
                fieldRef:
                  fieldPath: metadata.name
            - name: NODE_IP
              valueFrom:
                fieldRef:
                  fieldPath: status.hostIP
            - name: REPLICA_COUNT
              value: "1"
          resources:
            requests:
              cpu: 50m
              memory: 250Mi
            limits:
              cpu: 1000m
              memory: 1024Mi

---
apiVersion: v1
kind: Service
metadata:
  name: config-server-headless
  labels:
    app.kubernetes.io/name: config-server
    app.kubernetes.io/instance: config-server
spec:
  ports:
    - port: 36462
      targetPort: http
      protocol: TCP
      name: http
    - port: 36463
      targetPort: http
      protocol: TCP
      name: quorum
    - port: 36464
      targetPort: http
      protocol: TCP
      name: election
  selector:
    app.kubernetes.io/name: config-server
    app.kubernetes.io/instance: config-server
---
apiVersion: v1
kind: Service
metadata:
  name: config-server
  labels:
    app.kubernetes.io/name: config-server
    app.kubernetes.io/instance: config-server
spec:
  ports:
    - port: 36462
      targetPort: http
      protocol: TCP
      name: http
    - port: 36463
      targetPort: http
      protocol: TCP
      name: quorum
    - port: 36464
      targetPort: http
      protocol: TCP
      name: election
  selector:
    app.kubernetes.io/name: config-server
```