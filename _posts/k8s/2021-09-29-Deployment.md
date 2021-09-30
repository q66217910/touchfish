---
layout: post
title: Kubernetes之Deployment
category: k8s
tags: [k8s]
no-post-nav: true
---

## K8S-Deployment



### 1.控制器模型

1. Deployment 控制器从 Etcd 中获取到所有携带了“ selector.matchLabels”标签的 Pod，然后统计它们的数量，这就是实际状态；
2. Deployment 对象的 Replicas 字段的值就是期望状态；
3.  Deployment 控制器将两个状态做比较，然后根据比较结果，确定是创建 Pod，还是删除已有的 Pod 

