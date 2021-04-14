~~~sql
# 内部表和外部表
# 内部表 (管理表) , 当我们删除一个管理表时, Hive也会删除这个表中的数据
# 外部表, 删除外部表并不会删除表中的数据, 不过描述表的元数据信息会被删除掉


# 普通创建表
create table if not exists student(
	id int, name string
)
row format delimited fields terminated by '\t'
stored as textfile
location '/user/hive/student'

# 根据查询结果创建表 (查询的结果会添加到新创建的表中)
create table if not exists student as select id, name from student;

#根据已经存在的表结构创建表
create table if not exists student2 like student


# 创建外部表
create external table if not exists boluo.dept(
	deptno int,
    dname string,
    loc int
)
row format delimited fields terminated by '\t';


# 查看创建的表
show tables;

# 向外部表中导入数据
load data local inpath 'opt/module/datas/dept.txt' into table boluo.dept;

# 查询结果 
select * from emp;

# 查看表格式化数据
desc formatted dept;

~~~



~~~sql
# 内部表与外部表的相互转换

# 修改内部表student2为外部表
alter table student2 set tblproperties('EXTERNAL'='TRUE');

~~~

