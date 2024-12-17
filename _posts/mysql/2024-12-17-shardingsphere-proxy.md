---
layout: post
title: shardingsphere-proxy部署及使用
category: mysql
tags: [life]
no-post-nav: true
---

## 1. Docker-Compose 文件

### 配置说明
- **conf**: 该目录包含 ShardingSphere-Proxy 的相关配置。详细配置请参考 [官方文档](https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-proxy/yaml-config/)。
- **ext-lib**: 额外的驱动包，例如 MySQL 驱动 JAR 包。

### Docker-Compose 示例
```yaml
networks:
  shardingsphere-proxy:
    driver: bridge
    ipam:
      config:
        - subnet: 172.16.100.0/24

services:
  shardingsphere-proxy:
    image: docker.repo.swifer.co/apache/shardingsphere-proxy:5.5.1
    container_name: shardingsphere-proxy
    volumes:
      - ./conf:/opt/shardingsphere-proxy/conf
      - ./ext-lib:/opt/shardingsphere-proxy/ext-lib
    ports:
      - "3308:3308"
    deploy:
      resources:
        limits:
          cpus: "2.00"
          memory: 5G
    environment:
      - PORT=3308
    networks:
      - shardingsphere-proxy
```

## 2. 详情配置说明

### 官方示例
请参考 [此链接](https://github.com/apache/shardingsphere/blob/612cd5d8e802d0d712a3a4d89da8fdc048d23879/proxy/bootstrap/src/main/resources/conf/global.yaml#L71-L89)。

### 示例配置
```yaml
authority:
  users:
    - user: # 用于登录计算节点的用户名和授权主机的组合，格式：<username>@<hostname>，hostname 为 % 或空字符串表示不限制授权主机，username 和 hostname 大小写不敏感
      password: # 用户密码
      admin: # 可选项，管理员身份标识。若为 true，该用户拥有最高权限，缺省值为 false
      authenticationMethodName: # 可选项，用于为用户指定密码认证方式
  authenticators: # 可选项，默认不需要配置，Proxy 根据前端协议类型自动选择
    authenticatorName:
      type: # 密码认证类型
  defaultAuthenticator: # 可选项，指定一个 authenticatorName 作为默认的密码认证方式
  privilege:
    type: # 权限提供者类型，缺省值为 ALL_PERMITTED
```

## 3. 属性列表

### 官方文档地址
请查看 [属性配置文档](https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-proxy/yaml-config/props/)。

| 名称                                      | 数据类型 | 说明                                                                                               | 默认值           | 动态生效 |
|-----------------------------------------|--------|--------------------------------------------------------------------------------------------------|----------------|--------|
| system-log-level                        | String | 系统日志输出级别，支持 DEBUG、INFO、WARN 和 ERROR，默认级别是 INFO。                                   | INFO           | 是      |
| sql-show                                | boolean | 是否在日志中打印 SQL。打印 SQL 可以帮助开发者快速定位系统问题。                                 | true           | 否      |
| sql-simple                              | boolean | 是否在日志中打印简单风格的 SQL。                                                                  | false          | 否      |
| kernel-executor-size                   | int    | 用于设置任务处理线程池的大小。每个 ShardingSphereDataSource 使用一个独立的线程池。                 | infinite       | 否      |
| max-connections-size-per-query         | int    | 一次查询请求在每个数据库实例中所能使用的最大连接数。                                              | 1              | 是      |
| check-table-metadata-enabled            | boolean | 在程序启动和更新时，是否检查分片元数据的结构一致性。                                            | false          | 是      |
| load-table-metadata-batch-size         | int    | 在程序启动或刷新元数据时，单个批次加载表元数据的数量。                                           | 1000           | 是      |
| proxy-frontend-flush-threshold          | int    | 在 ShardingSphere-Proxy 中设置传输数据条数的 IO 刷新阈值。                                        | 128            | 是      |
| proxy-backend-query-fetch-size          | int    | Proxy 后端与数据库交互的每次获取数据行数。                                                       | -1             | 是      |
| proxy-frontend-executor-size            | int    | Proxy 前端 Netty 线程池线程数量，默认值 0 代表使用 Netty 默认值。                                   | 0              | 否      |
| proxy-frontend-max-connections          | int    | 允许连接 Proxy 的最大客户端数量，默认值 0 代表不限制。                                           | 0              | 是      |
| proxy-default-port                      | String | Proxy 通过配置文件指定默认端口。                                                                   | 3307           | 否      |
| proxy-netty-backlog                    | int    | Proxy 通过配置文件指定默认 netty back_log 参数。                                                 | 1024           | 否      |
| proxy-frontend-database-protocol-type   | String | Proxy 前端协议类型，支持 MySQL，PostgreSQL 和 openGauss。                                        | ""             | 否      |
| proxy-frontend-ssl-enabled              | boolean | Proxy 前端启用 SSL/TLS。                                                                            | false          | 否      |
| proxy-frontend-ssl-version              | String | 要启用的 SSL/TLS 协议。空白以使用默认值。                                                          | TLSv1.2,TLSv1.3| 否      |
| proxy-frontend-ssl-cipher               | String | 按偏好顺序启用的密码套件。用逗号分隔的多密码套件。空白以使用默认值。                             | ""             | 否      |

## 4. 数据库配置及分表规则

### 数据库配置
相关文档地址：[数据源配置文档](https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-proxy/yaml-config/data-source/)。

```yaml
databaseName: rdp
dataSources: # 数据源配置，可配置多个
  <data-source-name>: # 数据源名称
    dataSourceClassName: # 数据源连接池完整类名
    url: # 数据库 URL 连接
    username: # 数据库用户名
    password: # 数据库密码
    # ... 数据库连接池的其它属性
```

### 配置说明
- **_SINGLE**: 代表单表无须分区分表，默认所有表都无须分区分表。
- **_SHARDING**: 要进行分区分表的表。

```yaml
rules:
  - !SHARDING
    tables:
      <tableName>:
  - !SINGLE
    tables:
      - "*.*"
```

### 分区表分片配置
```yaml
tables:
  <table_name>: # 要进行分库分表的表名
    actualDataNodes: rdp.tb_transaction_${2012..2030}01,rdp.tb_transaction_${2012..2030}02,rdp.tb_transaction_${2012..2030}03,rdp.tb_transaction_${2012..2030}04,rdp.tb_transaction_${2012..2030}05,rdp.tb_transaction_${2012..2030}06,rdp.tb_transaction_${2012..2030}07,rdp.tb_transaction_${2012..2030}08,rdp.tb_transaction_${2012..2030}09,rdp.tb_transaction_${2012..2030}${10..12}
    tableStrategy:
      standard:
        shardingColumn: timestamp_yymmdd
        shardingAlgorithmName: tb_transaction_inline
    keyGenerateStrategy:
      column: id
      keyGeneratorName: snowflake
    shardingAlgorithms:
      tb_transaction_inline: # 分表算法名称
        type: INTERVAL
        props:
          datetime-pattern: "yyyy-MM-dd HH:mm:ss"  # 分片字段格式
          datetime-lower: "2000-01-01 00:00:00"  # 范围下限
          datetime-upper: "2300-01-01 00:00:00"  # 范围上限
          sharding-suffix-pattern: "yyyyMM"  # 分片名后缀
          datetime-interval-amount: 1  # 分片间隔
          datetime-interval-unit: "MONTHS" # 分片间隔单位
```

### 分表配置
- **_actualDataNodes**: 为分片规则的模板，模板中需包含 `${}`，其中 `${}` 中填写的变量会根据分片规则的分片值进行替换。
- **tableStrategy**: 表的分片策略。
    - **shardingColumn**: 分片列名。
    - **shardingAlgorithmName**: 分片算法名称。
- **keyGenerateStrategy**: 主键生成策略。
    - **column**: 分布式主键列名。
    - **keyGeneratorName**: 主键生成算法名称。

### 分表算法配置
- **type**: 分片算法类型（类型示例: INTERVAL, MOD）。相关文档请查看 [分片算法文档](https://shardingsphere.apache.org/document/current/cn/user-manual/common-config/builtin-algorithm/)。
- **props**: 算法详细属性配置。

## 5.使用方式

1. 启动shardingsphere-proxy。
2. 对分库分表的数据进行迁移
3. 将客户端的数据库连接信息改为shardingsphere-proxy的连接信息。






