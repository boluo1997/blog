

# 每个用户截止到每月为止的最大单月访问次数和累计到该月的总访问次数，结果数据格式如下:

数据：
userid,	month,		visits
A,		2015-01,	5
A,		2015-01,	15
B,		2015-01,	5
A,		2015-01,	8
B,		2015-01,	25
A,		2015-01,	5
A,		2015-02,	4
A,		2015-02,	6
B,		2015-02,	10
B,		2015-02,	5
A,		2015-03,	16
A,		2015-03,	22
B,		2015-03,	23
B,		2015-03,	10
B,		2015-03,	1


#这个操作会把每个用户每个月的访问量求和
select userid, month, sum(visits) visits
from st
group by `month`, `userid`;

#结果
userid  month	visits
A		01		20
B		01		21
A		02		18


select userid, month, visits, 
max(visits) over(distribute by userid sort by month) maxvisit,
sum(visits) over(distribute by userid sort by month) totalvisit
from(
	select userid, month, sum(visits) visits
	from st
	group by `month`, `userid`
);

