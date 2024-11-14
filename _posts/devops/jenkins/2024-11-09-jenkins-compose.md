---
layout: post
title: Jenkins(二)服务启动
category: jenkins
tags: [life]
no-post-nav: true
---

## 一. 定义环境变量common.env

common.env： 定义了主节点和从节点都会使用到的环境变量（比如字符集）

```text
LC_ALL=C.UTF-8
LANG=C.UTF-8
JAVA_TOOL_OPTIONS="-Dsun.jnu.encoding=UTF-8 -Dfile.encoding=UTF-8"
```

## 二. 启动主节点

* common.env： 定义了主节点和从节点都会使用到的环境变量（比如字符集）
* config.env： 主节点自定义环境变量
* 挂载目录/var/jenkins_home： jenkins的主目录,存储了jenkins的相关信息
* 挂载目录/home/jenkins/workspace： 执行任务的工作目录
* 挂载目录/usr/share/jenkins/ref/init.groovy.d： 初始化脚本，用于后续初始化内容，下面章节介绍
* 监听8080/50000端口

```yaml
version: '3.3'

services:
  jenkins:
    container_name: jenkins-master
    build: 
      context: ./container
    image: jenkins/jenkins-master:lts
    ports:
      - '8080:8080'
      - '50000:50000'
    env_file:
      - ../conf/config.env
      - ../conf/common.env
    volumes:
      - ./data/agent:/var/jenkins_home
      - ./data/workspace:/home/jenkins/workspace
      - ./init.groovy.d:/usr/share/jenkins/ref/init.groovy.d
```

## 三. 启动从节点

* JENKINS_SECRET: 远程连接的ssh密钥（值为master节点创建的node节点时生成的）
* JENKINS_AGENT_NAME： 节点名称
* 挂载目录/home/jenkins/workspace： 执行任务过程的工作空间

```yaml
version: '3.3'

services:
  jenkins-agent:
    build:
      context: container
    image: jenkins/jenkins-agent:lts
    network_mode: host
    env_file:
      - ../conf/common.env
      - ../conf/config.env
    environment:
      JENKINS_URL: http://localhost:8080
      JENKINS_SECRET: b69a5081fdfe4f0db2763f9fd5655247c067cf57f236502e8438439793ce56f3
      JENKINS_AGENT_NAME: node1
    volumes:
      - ./data/workspace:/home/jenkins/workspace
      - ./data/agent:/home/jenkins/agent
      - ./data/repo:/home/jenkins/repo
```