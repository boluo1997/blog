glom：将同一个分区的数据直接转换为相同类型的内存数组进行处理，分区不变



计算所有分区最大值求和（分区内取最大值，分区间最大值求和）

~~~scala
val rdd : RDD[Int] = sc.makeRDD(List(1,2,3,4), 2)

val glomRDD: RDD[Array[Int]] = rdd.glom()

val maxRDD: RDD[Array[Int]] = glomRDD.map(
    array => {
        array.max
    }
)

maxRDD.collect().sum
~~~

