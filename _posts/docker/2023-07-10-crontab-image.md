---
layout: post
title: crontab定时任务镜像制作
category: docker
tags: [docker]
no-post-nav: true
---

# crontab定时任务镜像制作

## 1.dockerfile编写
* FROM中的基础镜像可根据跑的脚本语言使用
* 通过*RUN install_packages vixie-cron crontabs*安装crontab,安装完成后通过crond -n显示启动
* 使用ENV设置当前容器服务的时区
```Dockerfile
FROM bitnami/python:3.9.17-debian-11-r10
ENV TZ=Asia/Shanghai 
RUN install_packages vixie-cron crontabs
CMD crond -n -s -x sch
```

## 2.crontab定时任务的添加
编写crontab文件
* TZ的作用为设置crontab的时区,与docker镜像设置的时区保持一致
```crontab
CRON_TZ=Asia/Shanghai
TZ=Asia/Shanghai

*/3 * * * * sh ./test_crontab.sh &
```

## 3.将crontab挂载到容器中
将步骤2编写的crontab文件挂载到/etc/cron.d目录下

Docker运行:
```shell
docker run -d -v /app/crontab:/etc/cron.d/crontab ${image}
```

K8S中: 可通过ConfigMap或者Secrets挂载的方式