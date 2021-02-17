Mybatis结构

![image-20210217181839674](../Data%20Structure/$%7Bimages%7D/image-20210217181839674.png)



使用Mybatis步骤：

- 编写配置文件sqlMapConfig.xml 配置数据源

  ~~~xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration
          PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
      <!-- 配置数据源 -->
      <environments default="mySql02">
          <environment id="mySql02">
              <transactionManager type="JDBC"/>
              <dataSource type="POOLED">
                  <property name="driver" value="com.mysql.jdbc.Driver"/>
                  <property name="url" value="jdbc:mysql:///mybatisdb"/>
                  <property name="username" value="root"/>
                  <property name="password" value="root"/>
              </dataSource>
          </environment>
      </environments>
      <!--配置映射文件-->
      <mappers>
          <mapper resource="userMapper.xml"></mapper>
      </mappers>
  </configuration>
  ~~~

  

- 编写映射文件，SQL语句就写在这里面

  ~~~xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <mapper namespace="cn.tedu.UserMapper">
      <select id="selc01" resultType="cn.tedu.domain.User">
          select * from user where id = 2;
      </select>
  </mapper>
  ~~~

  

调用的时候，会通过配置文件找到映射文件，并执行其中的SQL语句

#{}传值时自动拼接引号，${}拼接时不会自动拼接引号（拼接列名时使用）

Mybatis可以自动将查询出来的结果封装到bean中，前提是bean的属性名和查询出的列名相同，就会依次对应存储。



缓存机制可以减轻数据库压力，原理是在第一次查询的时候，将查询结果缓存起来，之后再查询相同的SQL，并不是真正的去查数据库，而是直接返回缓存中的结果

缓存可以降低数据库的压力，但是可能无法得到最新的结果数据



Mybatis缓存机制：

- 一级缓存：缓存只在一个事务中有效，即同一个事务中先后执行多次同一个查询，只在第一次真正去查数据库，并将结果缓存，之后的查询都直接获取缓存中的数据，如果是不同的事务，则缓存是隔离的。
- 二级缓存：缓存在全局有效，一个事务查询一个sql得到结果，会被缓存起来，之后只要缓存未被清除，则其他事务如果查询同一个sql，得到的将会是之前缓存的结果。二级缓存作用范围大，作用时间长，可能造成的危害也更大，所以在开发中一般很少启用Mybatis的二级缓存

Mybatis默认也是启动一级缓存，关闭二级缓存的。



## 多表设计 

- 一对一：在任意一方设计外键保存另一张表的主键
- 一对多：在多的一方设计外键保存一的一方的主键
- 多对多：设计一张第三方关系表，存储两张表的主键的对应关系，将一个多对多拆成两个一对多来存储

















