---
layout: post
title: 类加载机制
category: jvm
tags: [life]
no-post-nav: true
---

# 类加载机制

### 1.HotSpot虚拟机

Sun/Oracle公司的HotSpot虚拟机，在java运行时按需加载class，在需要用到该类的时候加载这个类。



### 2.类的加载过程

1. **加载（Loading）**
2. **验证（Verification）**
3. **准备（preparation）**
4. **解析（Resolution）**
5. **初始化（Initilization）**
6. **使用（using）**
7. **卸载（Unloading）**



### JVM参数

| 参数                   | 功能               |
| ---------------------- | ------------------ |
| -XX:+TraceClassLoading | 打印出类的加载顺序 |
|                        |                    |
|                        |                    |





