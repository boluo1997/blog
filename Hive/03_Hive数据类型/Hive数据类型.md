- 基本数据类型

| Hive数据类型 | Java数据类型 |
| ------------ | ------------ |
| TINYINT      | byte         |
| SMALLINT     | short        |
| INT          | int          |
| BIGINT       | long         |
| BOOLEAN      | boolean      |
| FLOAT        | float        |
| DOUBLE       | double       |
| STRING       | string       |
| TIMESTAMP    |              |
| BINARY       |              |



~~~java
// 在Hive的String类型相当于数据库的varchar类型, 该类型是一个不可变的字符串, 不过它不能声明其中最多能存储多少个字符.
~~~



- 集合数据类型

| 数据类型 | 描述                                                         | 语法示例 |
| -------- | ------------------------------------------------------------ | -------- |
| STRUCT   | 和C语言中的struct类似, 都可以通过.符号访问元素内容           | struct() |
| MAP      | Map是一组键值对元组集合, 使用数组表示法可以访问数据          | map()    |
| ARRAY    | 数组是一组具有相同类型的名称的变量的集合, 这些变量称为数组的元素, 每个数组元素都有一个编号, 编号从0开始 | Array()  |

~~~java
// Hive有三种复杂数据类型ARRAY、MAP和STRUCT. 复杂数据类型允许任意层次的嵌套
~~~

