~~~sql
SELECT
		zdi.`name`,
		IFNULL(count(zcs.share_id),0) 分享数
FROM
		zy_user_info zui
LEFT JOIN zy_content_share zcs on zcs.user_id = zui.user_id
LEFT JOIN zy_area za ON za.area_id = zui.area_id 
left join zy_dept_info zdi on zui.dept_id = zdi.dept_id
WHERE
		zui.status = 1
		and za.area_id = 209387
		GROUP BY zdi.dept_id
		
	是正确结果  换成	
		
		
SELECT
		zdi.`name`,
		IFNULL(count(zcs.share_id),0) 分享数
FROM
		zy_user_info zui
LEFT JOIN zy_content_share zcs on zcs.user_id = zui.user_id
LEFT JOIN zy_area za ON za.area_id = zui.area_id 
left join zy_dept_info zdi on za.area_id = zdi.area_id
WHERE
		zui.status = 1
		and za.area_id = 209387
		GROUP BY zdi.dept_id
		
就不正确		
		
		
		
		
		
		
		
		
		
~~~

