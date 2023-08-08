---
layout: post
title: windows使用make构建docker镜像
category: docker
tags: [docker]
no-post-nav: true
---

# windows使用make构建docker镜像

## 1.安装docker环境

下载[docker-desktop](https://www.docker.com/products/docker-desktop/)

## 2.编写dockerfile

例如：

```dockerfile
FROM nginx:1.23
ENV TZ=Asia/Shanghai
```

## 3.安装make环境

### 1.安装mingw64
[下载地址](https://udomain.dl.sourceforge.net/project/mingw-w64/Toolchains%20targetting%20Win64/Personal%20Builds/mingw-builds/8.1.0/threads-win32/seh/x86_64-8.1.0-release-win32-seh-rt_v6-rev0.7z)

### 2.设置IDEA-make目录
【build】-> 【make】

### 3.编写make文件
```makefile
.PHONY:	build push

REG_SERVER=
IMAGE=
TAG=

push: 
	docker buildx build -t $(REG_SERVER)/$(IMAGE):$(TAG) -f Dockerfile . --push
```

## 4.执行make文件
```shell
make push
```