---
layout: post
title: Kubernetes之Endpoint
category: k8s
tags: [k8s]
no-post-nav: true
---

## Endpoint

 Endpoint是可被访问的服务端点，即一个状态为running的pod。



### 1.Endpoints API

```yaml
apiVersion: v1
kind: Endpoints
metadata:
  namespace: zd
  name: zd-Endpoints
subsets:
  - addresses:
      # 此端点的 IP。 (127.0.0.0/8)、本地链路 (169.254.0.0/16) 或链路本地组播 ((224.0.0.0/24)。IPv6 也被接受
      - ip: 127.0.0.1
        #此端点的主机名
        hostname: blog.touchfish.com
        #托管此端点的节点。 这可用于确定节点本地的端点。
        nodeName: zd
        #对提供端点的对象的引用。
        targetRef:
          #对提供端点的对象的引用
          name: zd-Endpoints
          #所指对象的命名空间
          namespace: zd
          #所指对象的 API 版本
          apiVersion: v1
          kind: Endpoints
          #进行此引用的特定资源版本
          resourceVersion: v1
    ports:
        #端点的端口号
      - port: 80
        #此端口的名称。 这必须与相应 ServicePort 中的“名称”字段匹配。 必须是 DNS_LABEL。 仅当定义了一个端口时才可选。
        name: a
        #此端口的 IP 协议。 支持“TCP”、“UDP”和“SCTP”。 默认为 TCP
        protocol: TCP
    #提供相关端口但当前未标记为就绪的 IP 地址，因为它们尚未完成启动、最近未通过就绪检查或最近未通过活动检查。
  - notReadyAddresses:
      - ip: 127.0.0.1
```



### 2.Endpoints的功能

1.  **Endpoints表示一个Service对应的所有Pod副本的访问地址** 
2.  **Node上的Kube-proxy进程获取每个Service的Endpoints，实现Service的负载均衡功能。** 





### 3.**Endpoints Controller**

Endpoints Controller 用于负责生成和维护所有Endpoints的控制器。负责监听Service和对应Pod副本的变化。

1. 检测到Service被删除，则删除和该Service同名的Endpoints对象
2. 检测到新的Service被创建或修改，根据Service信息获得相关的Pod列表，创建或更新Endpoints