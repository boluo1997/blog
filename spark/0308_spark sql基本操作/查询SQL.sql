# 查询本列数据
select aa.stiid, aa.stiname, b.oi_date
from b 
right join 
(
	select a.oip_oi_id,
		   max(if(a.oip_key = 'sti_id',oip_value,null)) as stiid,
		   max(if(a.oip_key = 'sti_name',oip_value,null)) as stiname
	from a 
	where a.oip_key = 'sti_id' or a.oip_key = 'sti_name'
	group by a.oip_oi_id
) aa
on aa.oip_oi_id = b.oi_id


# 
select aa.diiId, aa.diiNumber, aa.diiType, b.oi_date
from b 
right join 
(
	select a.oip_oi_id, 
		   max(if(a.oip_key = 'dii_id', oip_value, null)) as diiId,
		   max(if(a.oip_key = 'dii_number', oip_value, null)) as diiNumber,
		   max(if(a.oip_key = 'dii_type', oip_value, null)) as diiType
	from a 
	where a.oip_key = 'dii_id' or a.oip_key = 'dii_number' or a.oip_key = 'dii_type'
	group by a.oip_oi_id
) aa
on aa.oip_oi_id = b.oi_id


#+-----+---------+-------+-------------------+
#|diiId|diiNumber|diiType|            oi_date|
#+-----+---------+-------+-------------------+
#|    4|      104|      1|2017-07-03 18:20:55|
#|    4|      104|      1|2017-07-03 18:24:26|
#|   38|      102|      1|2017-07-09 14:18:51|
#|   38|      102|      1|2017-07-09 14:18:51|
#|   38|      102|      1|2017-07-09 14:18:51|
#|   27|      102|      1|2017-07-09 20:31:11|
#|   27|      102|      1|2017-07-09 20:31:11|
#|   27|      102|      1|2017-07-09 20:31:11|


# 新值减去旧值
select handleName(beforeName, currentName)
from (
	select aa.stiid, aa.stiname as currentName, b.oi_date as currentDate, 
	lag(aa.stiname, 1, 'No1') over(order by aa.stiid) beforeName, 
	lag(b.oi_date, 1, '0000-0000') over(order by aa.stiid) beforeDate 
	from b 
	right join 
	(
		select a.oip_oi_id,
			   max(if(a.oip_key = 'sti_id',oip_value,null)) as stiid,
			   max(if(a.oip_key = 'sti_name',oip_value,null)) as stiname
		from a 
		where a.oip_key = 'sti_id' or a.oip_key = 'sti_name'
		group by a.oip_oi_id
	) aa
	on aa.oip_oi_id = b.oi_id
	order by b.oi_date
)


# 
select stiId, handleName(currentName), oiDate
from (
	select aa.stiid as stiId, aa.stiname as currentName, b.oi_date as oiDate
	from b 
	right join 
	(
		select a.oip_oi_id,
			   max(if(a.oip_key = 'sti_id',oip_value,null)) as stiid,
			   max(if(a.oip_key = 'sti_name',oip_value,null)) as stiname
		from a 
		where a.oip_key = 'sti_id' or a.oip_key = 'sti_name'
		group by a.oip_oi_id
	) aa
	on aa.oip_oi_id = b.oi_id
)




select c.stiid, c.stiname, c.oi_date, row_number() over(partition by c.stiid, c.stiname order by c.oi_date) rn
from c 
where rn = 1





{"name":""}

[{"op":"replace","path":"/name","value":""}]



select handleOrder(aa.diiId, aa.diiNumber, aa.diiType), b.oi_date
from b 
right join 
(
	select a.oip_oi_id, 
		   max(if(a.oip_key = 'dii_id', oip_value, null)) as diiId,
		   max(if(a.oip_key = 'dii_number', oip_value, null)) as diiNumber,
		   max(if(a.oip_key = 'dii_type', oip_value, null)) as diiType
	from a 
	where a.oip_key = 'dii_id' or a.oip_key = 'dii_number' or a.oip_key = 'dii_type'
	group by a.oip_oi_id
) aa
on aa.oip_oi_id = b.oi_id



