---
layout: post
title: Jfrog远程配置说明
category: devops
tags: [life]
no-post-nav: true
---

## Cache

![cache.png](https://blog.touchfishes.com/assets/images/2024/jfrog/cache.png)

### Unused Artifacts Cleanup Period (Hr) 

配置自动清理 “未使用” 小时数的制品

值为 0 表示禁用缓存制品的自动清理。

### Metadata Retrieval Cache Period (Sec)

远程服务器上检查较新版本之前缓存元数据文件的秒数。值为 0 表示无缓存。

### Metadata Retrieval Cache Timeout (Sec)

在提供本地缓存的构件或请求失败之前等待从远程检索的秒数

### Assumed Offline Period (Sec)

存储库在连接错误后保持假定脱机状态的秒数。在此时间结束时，将尝试进行在线检查以重置离线状态。
值为 0 表示存储库永远不会被视为脱机。

### Missed Retrieval Cache Period (Sec)

缓存构件检索未命中 （artifact not found） 的秒数。    值为 0 表示无缓存。

## Others

![other.png](https://blog.touchfishes.com/assets/images/2024/jfrog/other.png)

### Priority Resolution

在解析虚拟存储库时，设置 Priority Resolution 优先于解析顺序。

设置具有优先级的存储库将导致仅合并具有优先级的存储库中的元数据。

如果在这些存储库中找不到软件包，Artifactory 将从标记为非优先级的存储库中合并。

### Disable URL Normalization

禁用 URL 规范化

### Ignore Repository

设置后，存储库或其本地缓存不参与构件解析。

###  Allow Content Browsing

设置后，您可以直接从 Artifactory 查看 HTML 或 Javadoc 文件等内容。

这可能不安全，因此需要严格的内容审核，以防止恶意用户上传可能危及安全的内容（例如，跨站点脚本攻击）

###  Store Artifacts Locally 

设置后，存储库应在本地存储缓存的构件。

如果未设置，则构件不会存储在本地，而是使用直接存储库到客户端流。

这对于高速 LAN 上的多服务器设置非常有用，其中一个 Artifactory 将某些数据缓存在中央存储上，
并将其直接流式传输到卫星直通 Artifactory 服务器。

### Synchronize Properties

设置后，将获取远程工件及其属性。

### Bypass HEAD Requests

在缓存构件之前，Artifactory 首先向远程资源发送 HEAD 请求。

在某些远程资源中，不允许 HEAD 请求，因此会被拒绝，即使允许下载构件也是如此。

选中后，Artifactory 将绕过 HEAD 请求，并使用 GET 请求直接缓存构件。

### Block Mismatching Mime Types

如果设置，则根据系统属性文件中 blockedMismatchingMimeTypes 下指定的列表，
如果检测到请求的 mimetype 和接收的 mimetype 不匹配，则无法下载项目。

您可以通过将 mimetype 添加到下面的覆盖列表中来覆盖。

### Enable Direct Cloud Storage Download

设置后，对此存储库的下载请求将重定向客户端以直接从云存储提供商下载构件。

仅在 Enterprise+ 和 Edge 许可证中可用

