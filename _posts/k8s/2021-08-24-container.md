---
layout: post
title: LINUX容器介绍
category: container
tags: [container]
no-post-nav: true
---

## LINUX容器介绍



对于Linux容器来说,实际是运行在宿主机上的一个进程, **Cgroups 技术**是用来制造约束的主要手段，而**Namespace 技术**则是用来修改进程视图的主要方法。 



### 容器化优缺点

**优点:**

- **轻量级,没有性能的损耗**.容器化相当于宿主机的进程.
- **不额外占用资源:** Lunix支持namespace,不需要单独的部署虚拟机.

**缺点:**

- **隔离不够彻底:** namespace隔离不够彻底,实际上还是跑在同一台宿主机上, 在 Linux 内核中，有很多资源和对象是不能被 Namespace 化的.



## 1.Namespace

Namespace主要提供了**资源隔离方案**,即相互不同namespace的资源不可见.

-  **PID Namespace:**  进程隔离 
-  **Mount Namespace:**  挂载隔离  
-  **UTS Namespace:**   hostname隔离 
- **IPC  Namespace:**   消息隔离  
- **Network    Namespace:**  网络隔离  
- **User Namespace:** 用户/用户组隔离  



## 2.Cgroups

cgroups主要作用就是限制一个进程组能够使用的资源上限，包括 CPU、内存、磁盘、网络带宽等等,此外还能对进程进行优先级设置、审计，以及将进程挂起和恢复等操作.