#求出每个栏目的被观看次数及累计观看时长

数据:
vedio表
Uid channl min
1 	  1		23
2 	  1 	12
3 	  1 	12
4 	  1 	32
5 	  1 	342
6 	  2 	13
7 	  2 	34
8 	  2 	13
9 	  2 	134



select channel, count(1) num, sum(min) total 
from vedio 
group by channel 

