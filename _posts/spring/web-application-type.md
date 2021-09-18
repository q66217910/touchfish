#  WebApplicationType 

 WebApplicationType 的三种类型

```java
public enum WebApplicationType {

	/**
	 * 不启动内嵌的WebServer
	 */
	NONE,

	/**
	 * 启动内嵌的基于servlet的WebServer
	 */
	SERVLET,

	/**
	 * 启动内嵌的reactive web server
	 */
	REACTIVE;
}
```

