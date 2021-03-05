package src;

/* SimpleApp.java */
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;

/**
y_order_info表中有如下信息, 其中oi_id是与y_order_info_param表的关联字段,只取其中的oi_date字段即可

+-------+--------+--------------------+----------------+---------------+---------------+---------+-------------+-----------------+--------------------+--------------------+-------------+-----------+------------+----------------+-------+---------+-------------------+
|  oi_id|oi_ci_id|           oi_number|oi_refund_number|oi_refund_price|oi_introduction|oi_source|oi_real_money|oi_original_money|           oi_pay_id|         oi_trade_id|oi_pay_status|oi_pay_type|oi_refund_id|oi_refund_status|oi_type|oi_delete|            oi_date|
+-------+--------+--------------------+----------------+---------------+---------------+---------+-------------+-----------------+--------------------+--------------------+-------------+-----------+------------+----------------+-------+---------+-------------------+
|5429830|    9367|ON155075422694332...|            null|           null|       加强洗涤|        3|         7.00|             7.00|27d7f316bd9b4a659...|20190221220014233...|       normal|     支付宝|        null|            null|   1300|        0|2019-02-21 21:03:46|
|5429831|       1|ON155075423285460...|            null|           null|       标准洗涤|        1|         5.00|             5.00|                null|                null|         null|       null|        null|            null|   1450|        0|2019-02-21 21:03:52|

y_order_info_param表中有如下信息, 我们需要取到其中oip_key中的sti_id(店铺id)和sti_name(店铺名)字段,例如下表中数据sti_id = 157, sti_name = 兰德力141店

+---------+---------+---------------+-------------------+----------+-------------------+
|   oip_id|oip_oi_id|        oip_key|          oip_value|oip_delete|           oip_date|
+---------+---------+---------------+-------------------+----------+-------------------+
|187262926|  8467407|     start_date|2019-07-28 22:49:00|         0|2019-07-28 22:47:54|
|187262927|  8467407|       end_date|2019-07-28 23:40:00|         0|2019-07-28 22:47:54|
|187262928|  8467407|real_start_date|2019-07-28 22:48:16|         0|2019-07-28 22:47:54|
|187262929|  8467407|  real_end_date|2019-07-28 23:23:00|         0|2019-07-28 22:47:54|
|187262930|  8467407|         sii_id|                  2|         0|2019-07-28 22:47:54|
|187262931|  8467407|         spi_id|                220|         0|2019-07-28 22:47:54|
|187262932|  8467407|       spi_name|           标准洗涤|         0|2019-07-28 22:47:54|
|187262933|  8467407|         sti_id|                157|         0|2019-07-28 22:47:54|
|187262934|  8467407|       sti_name|        兰德力141店|         0|2019-07-28 22:47:54|
|187262935|  8467407|    sti_address| 浙大舟山校区男生楼|         0|2019-07-28 22:47:54|
|187262936|  8467407|         eti_id|                 38|         0|2019-07-28 22:47:54|
|187262937|  8467407|       eti_name|   浙江大学舟山校区|         0|2019-07-28 22:47:54|
|187262938|  8467407|    eti_address|          浙大路1号|         0|2019-07-28 22:47:54|
|187262939|  8467407|         cmb_id|              46065|         0|2019-07-28 22:47:54|
|187262940|  8467407|       plp_type|                  5|         0|2019-07-28 22:47:54|
|187262941|  8467407|      plp_price|               2.49|         0|2019-07-28 22:47:54|
|187262942|  8467407|       run_code|               5422|         0|2019-07-28 22:47:54|
|187262943|  8467407|         dii_id|                987|         0|2019-07-28 22:47:54|
|187262944|  8467407|     dii_number|                102|         0|2019-07-28 22:47:54|
|187262945|  8467407|       dii_type|                  1|         0|2019-07-28 22:47:54|
+---------+---------+---------------+-------------------+----------+-------------------+

*
*/  

public class boluoTest1 {
    public static void main(String[] args) {
        //String dataFile = "D:\\data\\dbv2\\y_device_info"; // Should be some file on your system
        String orderInfo = "D:\\data\\dbv2\\y_order_info";
        String orderParam = "D:\\data\\dbv2\\y_order_info_param";

        SparkSession spark = SparkSession.builder().master("local[*]").appName("Simple Application").getOrCreate();

        //Dataset<String> logData = spark.read().textFile(logFile).cache();
        //long numAs = logData.filter((FilterFunction<String>) s -> s.contains("a")).count();
        //long numBs = logData.filter((FilterFunction<String>) s -> s.contains("b")).count();
        //System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);

        /*df.select("dii_name","dii_code")
                .write()
                .mode(SaveMode.Overwrite)
                .csv("D:\\data\\demo\\boluo");*/

        Dataset<Row> df1 = spark.read().format("delta").load(orderInfo);
        //df1.printSchema();
        df1.show();

        Dataset<Row> df2 = spark.read().format("delta").load(orderParam);
        //df2.printSchema();
        df2.show();

        df1.registerTempTable("b");
        df2.registerTempTable("a");


        /**
          * 
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
          
          */

        Dataset ds =  spark.sql("select aa.stiid, aa.stiname, b.oi_date\n" +
                "from b \n" +
                "right join \n" +
                "(\n" +
                "select a.oip_oi_id,\n" +
                "\t\t\t max(if(a.oip_key = 'sti_id',oip_value,null)) as stiid,\n" +
                "\t\t   max(if(a.oip_key = 'sti_name',oip_value,null)) as stiname\n" +
                "from a \n" +
                "where a.oip_key = 'sti_id' or a.oip_key = 'sti_name'\n" +
                "group by a.oip_oi_id\n" +
                ") aa\n" +
                "on aa.oip_oi_id = b.oi_id");


        /**
          *
        select * 
        from (
            select c.stiid, c.stiname, c.oi_date, row_number() over(partition by c.stiid, c.stiname order by c.oi_date) rn
            from c 
        )
        where rn = 1  
          */  

        ds.registerTempTable("c");
        spark.sql("select * \n" +
                "from (\n" +
                "\t\tselect c.stiid, c.stiname, c.oi_date, row_number() over(partition by c.stiid, c.stiname order by c.oi_date) rn\n" +
                "from c \n" +
                ")\n" +
                "where rn = 1").show();


        ds.write().mode(SaveMode.Overwrite).csv("D:\\data\\demo\\boluo2");

        spark.stop();
    }
}

