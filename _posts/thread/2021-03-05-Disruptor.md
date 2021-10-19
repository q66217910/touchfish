---
layout: post
title: Disruptor
category: thread
tags: [life]
no-post-nav: true
---

## Disruptor

Disruptor是一个高性能队列，用于解决内存队列的延迟问题。



### 1.设计原理

- **环形数组结构：**

   为了避免垃圾回收，采用数组而非链表。同时，数组对处理器的缓存机制更加友好 

- 

