# Spinnaker各组件功能介绍

## 1.spin-deck(前端UI)

Deck是Spinnaker的前端，提供UI界面，提供管理Spinnaker的入口。

https://github.com/spinnaker/deck

## 2.spin-gate(对外API)

Gate是Spinnaker的后端，提供API接口，提供Spinnaker各组件之间的交互。

1. 提供Restful API接口。
2. 支持以下服务（CloudDriver/Front50/Igor/Orca）

https://github.com/spinnaker/gate

## 3.spin-orca(编排引擎)

Orca是Spinnaker的编排引擎，负责编排工作。

1. PIPELINE 
2. ORCHESTRATION

https://github.com/spinnaker/orca

## 4.spin-cloudDriver(云资源管理)

cloudDriver是Spinnaker的云驱动，负责云资源的管理。

https://github.com/spinnaker/clouddriver

## 5.spin-front50(配置中心)

front50是Spinnaker的配置中心，负责配置管理。

支持持久化：
1. Amazon S3
2. Google Cloud Storage
3. Redis
4. SQL - recommended （SQL是一种与云无关的存储后端，提供强大的写后读一致性和元数据版本控制。）

https://github.com/spinnaker/front50

## 6.spin-rosco(Helm 和 Kustomize)

Rosco是Spinnaker的云编译引擎，负责云编译工作。 负责Helm 和 Kustomize的编译和渲染。

https://github.com/spinnaker/rosco

## 7.spin-igor(持续集成和源控制管理)

Igor是为Spinnaker提供与持续集成(CI)和源控制管理(SCM)服务的单点集成的服务。

https://github.com/spinnaker/igor

## 8.spin-echo(事件引擎)

Echo是Spinnaker的事件引擎，负责事件的管理。

Echo在Spinnaker中有两个作用:
1. 一个事件路由器(例如，Igor检测到一个新的构建，它应该触发一个管道)
2. 用于CRON触发管道的调度器。

https://github.com/spinnaker/echo

## 9.spin-fiat(权限控制引擎)

Fiat是Spinnaker的权限控制引擎，负责权限的管理。

1. Accounts
2. Applications
3. Service Accounts

用户权限提供
1. Google Groups (through a Google Apps for Work organization)
2. GitHub Teams
3. LDAP
4. File based role provider
5. SAML Groups

https://github.com/spinnaker/fiat