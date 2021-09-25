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





## 3. **rootfs**(容器镜像)

 **rootfs**用于切换进程的根目录 

当你执行  *docker image inspect {xxx}:latest*  后，可以看镜像使用的 rootfs （由5层增量的rootfs组成）

```
"RootFS": {
            "Type": "layers",
            "Layers": [
               "sha256:87c8a1d8f54f3aa4e05569e8919397b65056aa71cdf48b7f061432c98475eee9",
               "sha256:25075874ce886bd3adb3b75298622e6297c3f893e169f18703019e4abc8f13f0",
               "sha256:caafc8119413c94f1e4b888128e2f337505fb57e217931a9b3a2cd7968340a9e",
               "sha256:e5d940a579ec4a80b6ec8571cb0fecf640dba14ccfd6de352977fd379a254053",
               "sha256:2a1c28c532d20c3b8af8634d72a4d276a67ce5acb6d186ac937c13bd6493c972",
               "sha256:1540b8226044ed5ce19cc0fec7fbfb36a00bb15f4e882d6affbd147a48249574"
            ]
        }
```

在使用镜像时，会把这些增量联合挂载在一个挂载点上（ /var/lib/docker/aufs/mnt/ ）



#### 镜像增量的联合挂载：

**容器的 rootfs 三部分组成** 

1. **只读层( readonly+whiteout )：**    对应着镜像的五层， 
2. **init层**  夹在只读层和读写层之间。Init 层是 Docker 项目单独生成的一个内部层，专门用来存放 /etc/hosts、/etc/resolv.conf 等信息 
3. **可读写层:**   当在容器中做写操作时，修改的内容会以增量的方式出现在这个层中。 whiteout的作用，在读写层创建一个文件，当联合挂载后，会屏蔽只读层中的这个文件。而docker commit和push是保持这个 被修改过的可读写层 。 原先的只读层里的内容则不会有任何变化 