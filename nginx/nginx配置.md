~~~shell
http {
#    include       mime.types;
#    default_type  application/octet-stream;
#    sendfile        on;
#    keepalive_timeout  65;

	#配置一个虚拟服务器
	server {
		#此虚拟服务器接收对80端口的访问
		listen 80;
		#此虚拟服务器接收对boluo主机名的访问 
		server_name www.boluo.com;

		location /{
			root boluo;
			index index.html;
		}
		
		#当访问/user资源时由此配置处理
		location /user {
			proxy_pass http://127.0.0.1:10001/user;
			add_header 'Access-Control-Allow-Credentials' 'true';
			add_header 'Access-Control-Allow-Origin' '*'; 
		}

		location /admin {
			proxy_pass http://127.0.0.1:10005/admin;
			add_header 'Access-Control-Allow-Credentials' 'true';
			add_header 'Access-Control-Allow-Origin' '*'; 
		}		
	}
~~~

