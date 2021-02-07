RESTful风格是一种风格，而不是一种标准，可以遵守也可以不遵守，只不过遵守RESTful风格的话，开发的接口更易维护。



没有使用RESTful风格前，我们的请求无外乎GET、POST

CRUD：

http://localhost:8080/selectUser?userId=1

http://localhost:8080/addUser?userName=boluo

http://localhost:8080/deleteUser?userId=1

http://localhost:8080/updateUser?userId=1&userName=boluo

传统的方式如果对一个对象要进行一个CRUD操作，服务端要编写四个URL地址，不利于维护



使用RESTful风格，服务端只需要发布一个URL地址，通过不同的请求方式来确定CRUD操作，http请求的不同方式，对应着不同的数据库操作



post		insert

get		  select

delete	 delete

put		  update



RESTful风格接口

~~~java
@RestController
public class UserController{
	
    @PostMapping("/user")	//只能接收post请求
    public User insertUser(User user){
        return user;
    }
    
    @GetMapping("/user")	//只能接收get请求
    public List<User> selectUser(){
        return list;
    }
    
    @DeleteMapping("user/{uid}")
    public User deleteUser(@PathVariable Integer uid){
        return user;
    }
    
    @PutMapping("/user")
    public User updateUser(User user){
        return user;
    }
}
~~~









