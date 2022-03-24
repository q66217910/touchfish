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

   加载classpath、jar包、网络、磁盘位置下的读取class文件二进制流,在内存中生成一个代表这个类的java.lang.Class对象放入元空间（即本地内存，好处在于加载类的数量不会占用java堆），**可以自定义类加载器来实现类的加载**

2. **验证（Verification）**

   验证class文件的字节流中包含的信息是否符合java虚拟机规范，确保虚拟机安全

3. **准备（preparation）**

   **类变量赋默认值，java final修饰值赋值成正式值**

4. **解析（Resolution）**

   **符号引用翻译为直接引用**

5. **初始化（Initilization）**

   **类的初始化**，调用类的静态属性与静态方法，new创建一个对象，反射API调用都会触发类的初始化，一个类初始化时，其父类也会被初始化

6. **使用（using）**

   **使用类**，调用类的属性与方法

7. **卸载（Unloading）**

   卸载条件：

   - 该类所有实例都已经被GC
   - 加载该类的ClassLoad已经被GC
   - 该类的java.lang.Class对象没有在任何地方被引用



### 3.类的初始化过程

1. 父类--静态变量（静态初始化阶段）
2. 父类--静态初始化块（静态初始化阶段）
3. 子类--静态变量（静态初始化阶段）
4. 子类--静态初始化块（静态初始化阶段）
5. 父类--变量（初始化阶段）
6. 父类--初始化块（初始化阶段）
7. 父类--构造器（初始化阶段）
8. 子类--变量（初始化阶段）
9. 子类--初始化块（初始化阶段）
10. 子类--构造器（初始化阶段）



### 4.类加载器

1. **启动类加载器（Bootstrap ClassLoader）**:  C语言实现，虚拟机自身的一部分

   加载{java_home}/jre/lib/rt.jar、resources.jar、charsets.jar,以及被-Xbootclasspath参数所指定的路径中存放的类库

2. **其他类加载器**：  java语言实现，独立于虚拟机，可以继承抽象类java.lang.ClassLoader

   - **ExtensionClassLoader(扩展类加载器):** {java_home}/jre/lib/ext
   - **AppClassLoader(应哟程序类加载器):** 加载用户类路径(即classPath)上所有的类库





### JVM参数

| 参数                   | 功能               |
| ---------------------- | ------------------ |
| -XX:+TraceClassLoading | 打印出类的加载顺序 |
|                        |                    |
|                        |                    |





