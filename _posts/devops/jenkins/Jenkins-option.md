---
layout: post
title: Jenkins-option
category: jenkins
tags: [life]
no-post-nav: true
---

Jenkins-option
===

option指令用于配置整个jenkins pipeline本身的选项



### 1.buildDiscarder

 保存最近历史构建记录的数量。当pipeline执行完成后，会在硬盘上保存记录。

```
buildDiscarder(logRotator(numToKeepStr: '3'))
```



### 2.checkoutToSubdirectory

 Jenkins从版本控制库拉取源码时，默认下载到当前job的根目录下 。

```
checkoutToSubdirectory('foo')
```



### 3.disableConcurrentBuilds

 同一个pipeline，Jenkins默认可以同时点击多次，并发执行。这样会消耗资源，并造成发布失败等问题。  配置并发锁定，可以在同时只允许一个执行，在和代码仓库进行联动的时候容易遇到，同时分支进行多次提交。 

```
disableConcurrentBuilds()
```



### 4.newContainerPerStage

 当agent为docker或dockerfile时，指定在同一个Jenkins节点上，每个stage都分别运行在一个新的容器中，而不是所有stage都运行在同一个容器中。 

```
newContainerPerStage()
```



### 5.retry

 当发生失败时进行重试，可以指定整个pipeline的重试次数。需要注意的是，这个次数是指总次数，包括第一次失败。 

```
retry(3)
```



### 6.timeout

如果pipeline执行时间过长，超出设置的timeout时间，Jenkins将中止pipeline。

以下例子中以小时为单位，还可以把SECONDS（秒），MINUTES（分钟）为单位。

```
timeout(time: 1, unit: 'HOURS')
```



### 7.skipDefaultCheckout

 在agent指令中，跳过从源代码控制中检出代码的默认情况。 

```
skipDefaultCheckout()
```



### 8.skipStagesAfterUnstable

 一旦构建状态变得UNSTABLE，就跳过该阶段。 

```
skipStagesAfterUnstable()
```



### 9.timestamps

 在stage块声明，每个步骤执行完，日志里都会打印执行时间。 

```
timestamps()
```



### 10. **overrideIndexTriggers** 

 允许覆盖分支索引触发器的默认处理。 

```
overrideIndexTriggers(false)
```









