~~~sql
Like
% 代表零个或多个字符 (任意个字符)
_ 代表一个字符

RLIKE子句是Hive中这个功能的一个扩展, 其可以通过Java的正则表达式来指定匹配条件

# 查找第二个数值为2的薪水的员工信息
select * from emp where sal like '_2%';

# 查找薪水中含有2的员工信息
select * from emp where sal like '[2]';
~~~



~~~sql
Group by 

# 计算emp每个部门中每个岗位的最高薪水
select t.deptno, t.job, max(t.sal) max_sal
from emp t
group by t.depton, t.job

# ceshi

~~~

