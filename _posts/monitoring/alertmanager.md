# [alert-manager](https://prometheus.io/docs/alerting/latest/alertmanager/)

AlertManager是处理客户端应用程序（如 Prometheus 服务器）发送的警报。它负责对它们进行重复数据删除、分组并将它们路由到正确的接收器集成，
例如电子邮件、PagerDuty 或 OpsGenie。它还负责静音和禁止警报。

## 一.AlertManager核心概念

### Grouping

Grouping将类似性质的警报分类到单个通知中。这在大型中断期间特别有用，因为许多系统同时发生故障，并且可能同时触发数百到数千个警报。

_**示例：** 当网络分区发生时，集群中正在运行数十个或数百个服务实例。一半的服务实例无法再访问数据库。
Prometheus 中的告警规则配置为在无法与数据库通信时为每个服务实例发送告警。因此，数百个警报被发送到
Alertmanager。作为用户，用户只想获得单个页面，
同时仍然能够准确查看哪些服务实例受到影响。_

因此，可以将 AlertManager 配置为按其集群和 alertname 对警报进行分组，以便它发送单个紧凑通知。
警报的分组、分组通知的时间以及这些通知的接收者由配置文件中的路由树配置。

### Inhibition

Inhibition是指在已触发某些其他警报时抑制某些警报的通知的概念

_**示例：** 正在触发警报，通知无法访问整个群集。可以将 AlertManager 配置为在触发特定警报时将有关此群集的所有其他警报静音。
这样可以防止收到成百上千个与实际问题无关的触发警报的通知。_

Inhibition通过 AlertManager 的配置文件进行配置。

### Silences

Silences是一种在给定时间内将警报静音的简单方法。静默是基于匹配器配置的，就像路由树一样。
检查传入警报是否与活动静默的所有相等或正则表达式匹配器匹配。如果他们这样做，则不会针对该警报发送任何通知。

Silences在 AlertManager 的 Web 界面中配置。

### Client behavior

AlertManager 对其客户端的行为有特殊要求。这些仅与不使用 Prometheus 发送警报的高级用例相关。

### High Availability

AlertManager 支持配置以创建集群以实现高可用性。这可以使用 --cluster-* 标志进行配置。重要的是不要对 Prometheus
及其 AlertManager 之间的流量进行负载均衡，而是将 Prometheus 指向所有 AlertManager 的列表。

## 二.AlertManager配置

Alertmanager 通过命令行标志和配置文件进行配置。命令行标志配置不可变的系统参数，而配置文件则定义禁止规则、通知路由和通知接收器。

### alertmanager.yml配置文件

若要指定要加载的配置文件，请使用 --config.file 标志。

```shell
./alertmanager --config.file=alertmanager.yml
```

该文件以YAML格式编写，由下面描述的方案定义。括号表示参数是可选的。对于非列表参数，该值设置为指定的默认值。

#### 通用占位符

* `<duration>` ：匹配的持续时间，例如 1d , 1h30m , 5m , 10s
* `<labelname>`: 与正则表达式 `[a-zA-Z_][a-zA-Z0-9_]*` 匹配的字符串
* `<labelvalue>`: Unicode 字符字符串
* `<filepath>`: 当前工作目录中的有效路径
* `<boolean>`: 可以取值 true 或 false 的布尔值
* `<string>`: 常规字符串
* `<secret>`: 作为机密的常规字符串，例如密码
* `<tmpl_string>`: 使用前经过模板扩展的字符串
* `<tmpl_secret>`: 在使用前经过模板扩展的字符串，该字符串是机密
* `<int>`: 整数值
* `<regex>`: 任何有效的 RE2 正则表达式（正则表达式锚定在两端。要取消锚定正则表达式


### [文件布局和全局设置](https://prometheus.io/docs/alerting/latest/configuration/)

全局配置指定在所有其他配置上下文中有效的参数。它们还用作其他配置部分的默认值。

```yaml
global:
  # The default SMTP From header field.
  [ smtp_from: <tmpl_string> ]
  # The default SMTP smarthost used for sending emails, including port number.
  # Port number usually is 25, or 587 for SMTP over TLS (sometimes referred to as STARTTLS).
  # Example: smtp.example.org:587
  [ smtp_smarthost: <string> ]
  # The default hostname to identify to the SMTP server.
  [ smtp_hello: <string> | default = "localhost" ]
  # SMTP Auth using CRAM-MD5, LOGIN and PLAIN. If empty, Alertmanager doesn't authenticate to the SMTP server.
  [ smtp_auth_username: <string> ]
  # SMTP Auth using LOGIN and PLAIN.
  [ smtp_auth_password: <secret> ]
  # SMTP Auth using LOGIN and PLAIN.
  [ smtp_auth_password_file: <string> ]
  # SMTP Auth using PLAIN.
  [ smtp_auth_identity: <string> ]
  # SMTP Auth using CRAM-MD5.
  [ smtp_auth_secret: <secret> ]
  # The default SMTP TLS requirement.
  # Note that Go does not support unencrypted connections to remote SMTP endpoints.
  [ smtp_require_tls: <bool> | default = true ]

  # The API URL to use for Slack notifications.
  [ slack_api_url: <secret> ]
  [ slack_api_url_file: <filepath> ]
  [ victorops_api_key: <secret> ]
  [ victorops_api_key_file: <filepath> ]
  [ victorops_api_url: <string> | default = "https://alert.victorops.com/integrations/generic/20131114/alert/" ]
  [ pagerduty_url: <string> | default = "https://events.pagerduty.com/v2/enqueue" ]
  [ opsgenie_api_key: <secret> ]
  [ opsgenie_api_key_file: <filepath> ]
  [ opsgenie_api_url: <string> | default = "https://api.opsgenie.com/" ]
  [ wechat_api_url: <string> | default = "https://qyapi.weixin.qq.com/cgi-bin/" ]
  [ wechat_api_secret: <secret> ]
  [ wechat_api_corp_id: <string> ]
  [ telegram_api_url: <string> | default = "https://api.telegram.org" ]
  [ webex_api_url: <string> | default = "https://webexapis.com/v1/messages" ]
  # The default HTTP client configuration
  [ http_config: <http_config> ]

  # ResolveTimeout is the default value used by alertmanager if the alert does
  # not include EndsAt, after this time passes it can declare the alert as resolved if it has not been updated.
  # This has no impact on alerts from Prometheus, as they always include EndsAt.
  [ resolve_timeout: <duration> | default = 5m ]

# Files from which custom notification template definitions are read.
# The last component may use a wildcard matcher, e.g. 'templates/*.tmpl'.
templates:
  [ - <filepath> ... ]

# The root node of the routing tree.
route: <route>

# A list of notification receivers.
receivers:
  - <receiver> ...

# A list of inhibition rules.
inhibit_rules:
  [ - <inhibit_rule> ... ]

# DEPRECATED: use time_intervals below.
# A list of mute time intervals for muting routes.
mute_time_intervals:
  [ - <mute_time_interval> ... ]

# A list of time intervals for muting/activating routes.
time_intervals:
  [ - <time_interval> ... ]
```


## 三.通知

### 通知报文

https://prometheus.io/docs/alerting/latest/notifications/