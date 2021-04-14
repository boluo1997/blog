~~~sql
# 创建数据库, 指定数据在HDFS上存放的位置
create database if not exists db_hive location '/db_hive.db';

# 过滤显示查询的数据库
show databases like 'db_hive*';

# 显示数据库详细信息
desc database extended db_hive;

# 切换当前数据库
use db_hive;

# 修改数据库
# 注意: 数据库的其他元数据信息都是不可更改的, 包括数据库名和数据库所在的目录位置
alter database db_hive set dbproperties('createtime'='20210410');


# 删除数据库
drop database if exists db_hive;

# 有数据的数据库要强制删除
drop database db_hive cascade;


~~~

