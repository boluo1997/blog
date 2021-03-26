#求连续7天登录的总人数

数据:
t1表
Uid 	dt 				login_status(1登录成功,0异常)
1 		2019-07-11 			1
1 		2019-07-12 			1
1 		2019-07-13 			1
1 		2019-07-14 			1
1 		2019-07-15 			1
1 		2019-07-16 			1
1 		2019-07-17 			1
1 		2019-07-18 			1
2 		2019-07-11 			1
2 		2019-07-12 			1
2 		2019-07-13 			0
2 		2019-07-14 			1
2 		2019-07-15 			1
2 		2019-07-16 			0
2 		2019-07-17 			1
2 		2019-07-18 			0
3 		2019-07-11 			1
3 		2019-07-12 			1
3 		2019-07-13 			1
3 		2019-07-14 			1
3 		2019-07-15 			1
3 		2019-07-16 			1
3 		2019-07-17 			1
3 		2019-07-18 			1


#第一步

select uid, dt 
from (
	select t1.uid uid,
	date_sub(t1.dt, t1.rm) dt 
	from 
	(
		select uid, dt, 
		row_number() over(distribute by uid sort by dt) rm 
		from login 
		where login.login_status = 1 
	) t1 
) t2
group by uid 
having count(uid) > 7;
