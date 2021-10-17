---
layout: post
title: Kubernetes之EndpointSlice
category: k8s
tags: [k8s]
no-post-nav: true
---

## EndpointSlice

一个 EndpointSlice 包含对一组网络端点的引用 



### 1.EndpointSlice的API

```yaml
apiVersion: discovery.k8s.io/v1
kind: EndpointSlice
metadata:
  namespace: zd
  name: zd-EndpointSlice
#指定此 EndpointSlice 携带的地址类型。 此切片中的所有地址必须是相同类型。 该字段在创建后是不可变的
#IPv4: 表示 IPv4 地址
#IPv6: 代表一个 IPv6 地址
#FQDN: 代表完全合格的域名
addressType: IPv4
endpoints:
  - addresses:
      - "127.0.0.1"
    conditions:
      #ready: 表示此Endpoint已准备好接收流量，具体取决于管理Endpoint的任何系统。
      #false: 表示未知状态。 在大多数情况下，消费者应该将此未知状态解释为就绪。
      ready: true
      #services 与 ready 相同，除了它的设置与Endpoint的终止状态无关 ,可以使用 EndpointSliceTerminationCondition 功能门启用此字段。
      #true: 终止正在就绪状态的Endpoint
      #false: 消费者应遵循就绪条件
      serving: true
      #终止指示此Endpoint正在终止
      #true: 终止指示此Endpoint正在终止
      terminating: true
    #此Endpoint的主机名。 Endpoint的消费者可以使用该字段来区分Endpoint
    hostname: pod-1
    #表示托管此Endpoint的节点的名称
    nodeName: node-1
    #此Endpoint所在的区域的名称
    zone: us-west2-a
ports:
  #Endpoint的端口号
  - port: 80
    #此端口的名称。 这必须与相应 ServicePort 中的“名称”字段匹配。 必须是 DNS_LABEL。 仅当定义了一个端口时才可选。
    name: a
    #此端口的 IP 协议。 支持“TCP”、“UDP”和“SCTP”。 默认为 TCP
    protocol: TCP
```



### 2.EndpointSlice的状态

1. **Ready:**  *endpoints.conditions.ready*  

    映射到Pod的条件，会一直映射到设置为true的Pod上(*endpoints.nodeName*)

2. **serving:** *endpoints.conditions.serving*  

   于ready条件相同， 只是它不考虑终止状态 。

3. **Terminating:** *endpoints.conditions.terminating*  

    指示Endpoint是否正在终止的条件。对于 pod，这是设置了删除时间戳的任何 pod。 



### 3.拓扑信息的存储

 EndpointSlice 中的每个端点都可以包含相关的拓扑信息 。

- **node:** *endpoints.conditions.nodeName*  Endpoint所在的节点的名称 
- **zone:** *endpoints.conditions.zone*  Endpoint所在的区域 