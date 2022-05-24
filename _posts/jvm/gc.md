---
layout: post
title: JVM垃圾回收
category: jvm
tags: [life]
no-post-nav: true
---

# JVM垃圾回收



### 一.可达性分析算法

从GC ROOTS节点开始，根据引用关系向下搜索，搜索过程所走的路径