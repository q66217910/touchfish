---
layout: post
title: Kubernetes之Kube-proxy
category: k8s
tags: [k8s]
no-post-nav: true
---

## Kube-proxy

Kube-proxy 是 kubernetes 工作节点上的一个网络代理组件，运行在每个节点上。

**作用:**

1.  维护节点上的网络规则.
2.  发往 Service 的流量（通过ClusterIP和端口）负载均衡到正确的后端Pod.



### 工作原理

 **kube-proxy 监听 API server 中 资源对象（ service、 endpoint 、 endpointslices 、 node ）的变化 ，根据监听资源变化操作代理后端来为服务配置负载均衡 **

1. 若使用了EndpointSlice ，则监听EndpointSlice 。
2. 没有使用EndpointSlice ，则监听 Endpoint 
3.  启用了服务拓扑（ Service Topology ）, 可以让一个服务基于集群的 Node 拓扑进行流量路由。 











