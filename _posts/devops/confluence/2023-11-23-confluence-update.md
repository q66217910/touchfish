---
layout: post
title: confluence版本升级
category: docker
tags: [docker]
no-post-nav: true
---

confluence版本升级
===

# 一.部署文件准备

可参考项目: https://github.com/munding/docker-confluence

### 1.获取confluence-server官方镜像

镜像地址： docker.io/atlassian/confluence-server:${tag}

版本号:
可访问[https://hub.docker.com/r/atlassian/confluence-server/tags](https://hub.docker.com/r/atlassian/confluence-server/tags)
查看

### 2.下载破解工具

破解工具下载地址： https://zhile.io/2018/12/20/atlassian-license-crack.html

### 3.下载数据库驱动

若使用mysql,则需要mysql驱动

驱动地址： https://downloads.mysql.com/archives/get/p/3/file/mysql-connector-java-8.0.22.zip

### 4.准备docker-compose.yaml

```yaml
version: '3'
services:
  confluence:
    image: "atlassian/confluence-server"
    volumes:
      - ./atlassian-agent.jar:/var/atlassian/atlassian-agent.jar
      - ./mysql-connector-java-8.0.22.jar:/opt/atlassian/confluence/confluence/WEB-INF/lib/mysql-connector-java-8.0.22.jar
      - /your-confluence-home:/var/atlassian/application-data/confluence
    environment:
      - JAVA_OPTS="-javaagent:/var/atlassian/atlassian-agent.jar"
      - JVM_MINIMUM_MEMORY=2048m
      - JVM_MAXIMUM_MEMORY=2048m
      - JVM_RESERVED_CODE_CACHE_SIZE=512m
    ports:
      - "8090:8090"
    restart: always
```

# 二.数据库升级

由于从低版本到高版本的过程中, 数据库及其表结构的字符集由utf-8需要改变成uft-8mb4,所以需要对数据库进行一个迁移

### 1.执行mysqldump,导出数据库

进入mysql目录

```shell
mysqldump --max-allowed-packet=1073741824 -u username -p confluence > confluence-$(date +%Y%m%d).sql
```
PS: --max-allowed-packet为转存大小,可视数据大小上调

### 2.替换导出数据的中的字符集

通过sed指令将utf-8替换成uft-8mb4

```shell
sed -i 's/utf8/utf8mb4/g' confluence-$(date +%Y%m%d).sql
sed -i 's/utf8_general_ci/utf8mb4_unicode_ci/g' confluence-$(date +%Y%m%d).sql
```

### 3.创建新数据库

```shell
CREATE DATABASE IF NOT EXISTS confluence8  DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_bin;
```

### 4.导入数据到新库

```shell
mysql -u username -p confluence8

source confluence-$(date +%Y%m%d).sql
```

# 三.版本升级

### 1.查看confluence.cfg.xml文件

`confluence.cfg.xml`目录为`/var/atlassian/application-data/confluence`下

重点关注以下三个参数
* atlassian.license.message： 破解的license
* confluence.setup.server.id： 服务server.id
* hibernate.connection.url： 数据库地址

```xml
<?xml version="1.0" encoding="UTF-8"?>

<confluence-configuration>
  <setupStep>complete</setupStep>
  <setupType>custom</setupType>
  <buildNumber>8505</buildNumber>
  <properties>
    <property name="atlassian.license.message">${license}</property>
    <property name="confluence.setup.server.id">${server.id}</property>
    <property name="hibernate.connection.url">${db}</property>
  </properties>
</confluence-configuration>

```

### 2.修改数据库

将`confluence.cfg.xml`中`hibernate.connection.url`的数据库替换为我们新的数据库

### 3.license处理

升级过程中可能会导致原license不可用,需要进行重新破解

进入容器
```shell
docker exec -it confluence bash 
```

并执行
```shell
java -jar /var/atlassian/atlassian-agent.jar -d -m test@test.com -n BAT -p 'conf' -o http://localhost:8090 -s ${confluence.setup.server.id}
```

将生成的license替换`confluence.cfg.xml`中`atlassian.license.message`即可


### 4.版本升级路径

由于升级过程需要不断的更新数据库表结构,所以需要按照指定升级路径进行升级

```
7.4.10 -> 7.9.3 -> 7.14.4 -> 8.3.2 -> 8.6.1
```


# 四.插件安装

### 1.插件下载

官方插件下载地址： https://marketplace.atlassian.com/

### 2.安装插件

`站点管理` -> `查找新应用` -> 选择应用 -> `安装`或者`免费试用`

### 3.破解

应用秘钥在管理应用中点击应用详情可以获取

执行
```shell
java -jar /var/atlassian/atlassian-agent.jar -d -m test@test.com -n BAT -p ${应用密钥} -o http://localhost:8090 -s ${confluence.setup.server.id}
```

将生成的license贴入并点击更新