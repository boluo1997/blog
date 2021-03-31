package com.boluo;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.landeli.biz.Outputs;
import com.landeli.hatchery.table.common.My;
import com.landeli.spark.Jdbcs;
import com.landeli.spark.Sparks;
import com.mysql.cj.result.RowList;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.api.java.UDF2;
import org.apache.spark.sql.api.java.UDF3;
import org.apache.spark.sql.catalyst.encoders.RowEncoder;
import org.apache.spark.sql.execution.datasources.jdbc.JdbcOptionsInWrite;
import org.apache.spark.sql.types.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.apache.spark.sql.functions.*;

public class Show2 {

	public static void main(String[] args) throws Exception {
		SparkSession spark = Sparks.builder()
				.getOrCreate();
	
		Dataset<Row> df = spark.read().format("delta").load("file:///d:/data/base2");

//		此时df中的数据是这样的, 主要是根据expend中的开始时间和结束时间, 把数据拆分到每一天, payment是一个数组, 也要拆开, 所以要拆两次
//		+----------------------------------------------------------------------------------+-----------------------------------------------+
//		|payment                                                                           |expend                                         |
//		+----------------------------------------------------------------------------------+-----------------------------------------------+
//		|[[2021-03-18 09:00:00, 支出.居间费, 江西昊潮科技有限公司, 791907229510772, 1563161,]]|[JJF2021030504311,备注, 2020-12-01, 2021-03-01]|

//		思路: 自定义函数, 把开始时间和结束时间传进去, 传出一个数组, 是时间中的每一天, 这个函数执行完之后, 会得到一个时间数组, 再根据返回的时间数组用explode()函数拆开
		
		//自定义的函数必须放前面
		spark.udf().register("handleDate",new UDF2<Date, Date, List<Date>>(){
			@Override
			public List<Date> call(Date startDate, Date endDate) {
				List<Date> dateList = Lists.newArrayList();
				if(startDate == null || endDate == null){
					return null;
				}
				for(LocalDate startDay = startDate.toLocalDate(); startDay.compareTo(endDate.toLocalDate()) < 0; startDay = startDay.plusDays(1)){
					dateList.add(Date.valueOf(startDay));
				}

				return dateList;
			}
		}, ArrayType.apply(DateType$.MODULE$));


		//编程思路: 先过滤数据, 再一个一个拆分
		Dataset<Row> df2 = df.where("format = 'qingliu.order'")
				.withColumn("payment", explode(col("payment")))
				.withColumn("dateDay", explode(expr("handleDate(expend.`开始时间`, expend.`结束时间`)")))
				.withColumn("countDays", datediff(expr("expend.`结束时间`"), expr("expend.`开始时间`")));

		//拆分完之后把金额平均到每一天, 这种方法有一个缺点, 就是除不尽的金额会丢失
		df2.select(expr("expend.*"), expr("payment.*"), col("dateDay"), col("countDays"))
				.withColumn("金额", expr("round(`金额`/`countDays`)"))
				.drop("开始时间", "结束时间", "ts", "countDays")
				.show(false);


	}

}
