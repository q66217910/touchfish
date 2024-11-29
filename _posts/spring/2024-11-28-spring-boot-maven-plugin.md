---
layout: post
title: spring-boot-maven-plugin 构建镜像
category: spring
tags: [springboot]
no-post-nav: true
---

## plugin配置image

* name: 镜像的名称,可以使用${project.artifactId}:${project.version},使用当前项目的artifactId和version
* builder： 用于构建的镜像,可以用于自定义镜像,例如: maven:3.8.4-eclipse-temurin-17-alpine，一些特殊环境
* env: 环境变量
* cleanCache： 是否应在构建前清理缓存
* verboseLogging: 是否需要详细日志
* pullPolicy: 是否应从远程存储库拉取镜像(ALWAYS/NEVER/IF_NOT_PRESENT)
* publish: 是否将构建的镜像推送到远程存储库
* tags: 镜像的标签

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <goal>build-image</goal>
        <image>
            <name>${project.artifactId}</name>
        </image>
        <layers>
            <layer>
            </layer>
        </layers>
        <excludes>
            <exclude>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
            </exclude>
        </excludes>
    </configuration>
</plugin>
```