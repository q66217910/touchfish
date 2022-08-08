# nginx gzip模块

### 一.[配置](https://nginx.org/en/docs/http/ngx_http_gzip_module.html#example)

##### 1.gzip

是否启用gzip

| Syntax:  | gzip on \| off               |
| :------- | ---------------------------- |
| Default: | gzip off                     |
| Context: | `http`, `server`, `location` |



##### 2. **gzip_buffers** 

设置用于压缩响应的缓冲区的数量和大小

| Syntax:  | `gzip_buffers number size;`  |
| :------- | ---------------------------- |
| Default: | `gzip_buffers 32 4k|16 8k;`  |
| Context: | `http`, `server`, `location` |



##### 3. **gzip_comp_level** 

设置响应的 gzip 压缩级别,级别由1-9

 1 压缩比最小处理速度最快，9 压缩比最大但处理最慢 

| Syntax:  | `gzip_comp_level level;`     |
| :------- | ---------------------------- |
| Default: | gzip_comp_level 1;           |
| Context: | `http`, `server`, `location` |



##### 4. **gzip_disable** 

禁用对具有匹配任何指定正则表达式的“User-Agent”标头字段的请求的响应的 gzip 压缩。

| Syntax:  | **gzip_disable** `*regex*` ...; |
| :------- | ------------------------------- |
| Default: |                                 |
| Context: | `http`, `server`, `location`    |



##### 5. **gzip_http_version** 

设置压缩响应所需的请求的最低 HTTP 版本

| Syntax:  | **gzip_http_version** 1.0 \| 1.1 |
| :------- | -------------------------------- |
| Default: | gzip_http_version 1.1;           |
| Context: | `http`, `server`, `location`     |



##### 6. **gzip_min_length** 

设置将被压缩的响应的最小长度。长度仅由“Content-Length”响应头字段确定。

| Syntax:  | **gzip_min_length** `*length*`; |
| :------- | ------------------------------- |
| Default: | gzip_min_length 20;             |
| Context: | `http`, `server`, `location`    |



##### 7. **gzip_proxied** 

根据请求和响应启用或禁用代理请求的响应 gzip 压缩。 请求被代理的事实是由“Via”请求头字段的存在决定的。 该指令接受多个参数

-  off : 禁用所有代理请求的压缩，忽略其他参数；
-  expired : 如果响应标头包含带有禁用缓存值的“Expires”字段，则启用压缩；
-  no-cache : 如果响应头包含带有“no-cache”参数的“Cache-Control”字段，则启用压缩；
-  no-store : 如果响应头包含带有“no-store”参数的“Cache-Control”字段，则启用压缩；
-  private : 如果响应头包含带有“private ”参数的“Cache-Control”字段，则启用压缩；
-  no_last_modified : 如果响应头不包含“Last-Modified”字段，则启用压缩；
-  no_etag :  如果响应头包含“ ETag ”字段，则启用压缩；
-  auth : 如果响应头包含“  Authorization ”字段，则启用压缩；
-  any : 为所有代理请求启用压缩。

| Syntax:  | gzip_proxied `off` | `expired` |`no-cache`|no-store| private|`no_last_modified`|no_etag|`auth`|`any` |
| :------- | ------------------------------------------------------------ |
| Default: | gzip_proxied off;                                            |
| Context: | `http`, `server`, `location`                                 |



##### 8. **gzip_types** 

除了“text/html”之外，还启用对指定 MIME 类型的响应的 gzip 压缩。 特殊值“*”匹配任何 MIME 类型 (0.8.29)。 “text/html”类型的响应总是被压缩的。

| Default: | gzip_types text/html;         |
| :------- | ----------------------------- |
| Syntax:  | **gzip_types** mime-type ...; |
| Context: | `http`, `server`, `location`  |



##### 9. **gzip_vary** 

如果指令 gzip、gzip_static 或 gunzip 处于活动状态，则启用或禁用插入“Vary: Accept-Encoding”响应头字段。

| Default: | **gzip_vary** on \| off      |
| :------- | ---------------------------- |
| Syntax:  | gzip_vary off;               |
| Context: | `http`, `server`, `location` |



推荐示例:

```nginx
server {
    listen 8080;
    
    gzip on;
	gzip_disable "msie6";

	gzip_vary on;
	gzip_proxied any;
	gzip_comp_level 6;
	gzip_buffers 16 8k;
	gzip_http_version 1.1;
	gzip_min_length 256;
	gzip_types
  		application/atom+xml
  		application/geo+json
  		application/javascript
  		application/x-javascript
  		application/json
  		application/ld+json
  		application/manifest+json
  		application/rdf+xml
  		application/rss+xml
  		application/xhtml+xml
 		application/xml
  		font/eot
  		font/otf
  		font/ttf
  		image/svg+xml
  		text/css
  		text/javascript
  		text/plain
  		text/xml;
    #cache 这类客户端不要会请求服务端，服务端改了配置和内容，客户端在这个期间也不会有感知的
    location ~* .(?:css|js|png|jpeg)$ {
  		expires 7d;
  		add_header Cache-Control "public";
	}
    #no-cache, 客户端会缓存但是每一个请求还是会带etag请求服务端
    location ~* .html$ {
  		expires 7d;
  		add_header Cache-Control "public, no-cache";
	}
  }

```



### 2.[HTTP Cache-Control](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/Cache-Control)

-  public :  表明响应可以被任何对象（包括：发送请求的客户端，代理服务器，等等）缓存，即使是通常不可缓存的内容。（例如：1.该响应没有`max-age`指令或`Expires`消息头；2. 该响应对应的请求方法是 [POST](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Methods/POST) 。） 
-  private :  表明响应只能被单个用户缓存，不能作为共享缓存（即代理服务器不能缓存它）。私有缓存可以缓存响应内容，比如：对应用户的本地浏览器。 
-  no-cache :   在发布缓存副本之前，强制要求缓存把请求提交给原始服务器进行验证 (协商缓存验证)。 
-  no-store :  缓存不应存储有关客户端请求或服务器响应的任何内容，即不使用任何缓存。 