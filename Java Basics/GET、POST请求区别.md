1.GET请求不安全，POST请求更安全

2.GET请求参数拼接在URL中，POST请求参数放在request body报文体中

3.GET请求在URL中传递参数长度不超过4K，POST没有限制

4.GET请求产生一个tcp数据包，POST请求产生两个数据包

对于GET方式的请求，浏览器会把 http header 和 data 一并发送出去，服务器响应200

对于POST方式的请求，浏览器先发送header，服务器响应100 continue，浏览器再发送data，服务器响应200