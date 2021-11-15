---
layout: post
title: Kubernetes之StatefulSet
category: k8s
tags: [k8s]
no-post-nav: true
---

## K8S-StatefulSet

StatefulSet多用于 主从关系、主备关系 。

### 1.StatefulSet的设计

-  **拓扑状态：**  应用的多个实例之间不是完全对等的关系。 必须按照某些顺序启动 。
-  **存储状态：**  应用的多个实例分别绑定了不同的存储数据。  对于这些应用实例来说，Pod A 第一次读取到的数据，和隔了十分钟之后再次读取到的数据，应该是同一份，哪怕在此期间 Pod A 被重新创建过。这种情况最典型的例子，就是一个数据库应用的多个存储实例。  



### 2.StatefulSet 的工作原理 

