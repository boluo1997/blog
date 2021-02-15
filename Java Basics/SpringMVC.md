SpringMVC执行流程

![image-20210215140815329](C:\Users\dingc\AppData\Roaming\Typora\typora-user-images\image-20210215140815329.png)





1.用户（客户端）发送请求通过web.xml到前端控制器（DispatchServlet）

2.前端控制器收到请求调用处理器映射器（HandlerMapping）

3.处理器映射器找到具体的处理器、生成处理器对象和处理器拦截器一并返还给前端控制器

4.前端控制器调用处理器适配器（HandlerAdapter）

5.处理器适配器经过适配调用具体的处理器（Handler）

6.7.处理器执行完成返回ModelAndView给前端控制器

8.前端控制器将ModelAndView传给视图解析器

9.视图解析器解析后返回给前端控制器具体的View

10.前端控制器根据View进行视图渲染

11.响应完成，把结果返回给用户

























