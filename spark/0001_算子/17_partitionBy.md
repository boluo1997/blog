key-value类型

partitionBy

~~~scala

val rdd = sc.makeRDD(List(1,2,3,4), 2)

val mapRDD:RDD[(Int, Int)] = rdd.map((_, 1))

//partitionBy根据指定的分区规则对数据进行重分区
mapRDD.paertitionBy(new HashPatitioner(2)).saveAsTextFile("output")


~~~





