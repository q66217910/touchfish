# 在gradle中使用junit，出现错误Test events were not received

## 一. 问题描述

Intellij IDEA中运行junit测试时，出现错误：Test events were not received.

点击运行时，运行的指令为
```shell
gradle :project-name:cleanTest :project-name:test --tests "some.class.some.method"
```

**原因是在新版本的idea中会自动使用默认的Gradle来运行测试，而Gradle默认的测试运行器是JUnit4。**

## 二. 解决方法

```
Preferences -> Build, Execution, Deployment -> Build Tools -> Gradle
```

1. 将Run tests using: Gradle中的Gradle修改为Intellij IDEA
2. 在Run/Debug Configurations中，把Gradle下的各种test删掉