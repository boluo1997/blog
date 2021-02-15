1.安装nginx

![image-20210210140802108](C:\Users\dingc\AppData\Roaming\Typora\typora-user-images\image-20210210140802108.png)



2.通过修改端口号的方式，启动多个程序。启动程序时加上server.port参数来指定不同的端口

![image-20210210140844063](C:\Users\dingc\AppData\Roaming\Typora\typora-user-images\image-20210210140844063.png)



![image-20210210141347910](C:\Users\dingc\AppData\Roaming\Typora\typora-user-images\image-20210210141347910.png)



3.修改nginx配置文件，进行负载均衡配置

~~~shell
upstream boluo{
	ip_hash;
	server localhost:8081;
	server localhost:8082;
	server localhost:8083;
}

server{
	listen 80;
	server_name www.boluo.com;
	location /{
		#转发到上面配置的服务器组
		proxy_pass http://boluo;
	}
}
~~~

配置代理

![image-20210210142332537](C:\Users\dingc\AppData\Roaming\Typora\typora-user-images\image-20210210142332537.png)



重启nginx







