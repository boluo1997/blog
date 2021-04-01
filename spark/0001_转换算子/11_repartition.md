repartition

扩大分区



~~~scala
val rdd = sc.makeRDD(List(1,2,3,4,5,6), 2)

//coalesce算子可以扩大分区，但是如果不进行shuffle操作，是没有意义的(多余了分区)
//所以如果想扩大分区, 需要使用shuffle操作

// val newRDD : RDD[Int] = rdd.coalesce(3)
val newRDD : RDD[Int] = rdd.coalesce(3, true)

//spark提供了一个简化的操作
//缩减分区: coalesce, 如果想要数据均衡, 可以采用shuffle
//扩大分区: repartition, 底层代码调用的就是coalesce, 而且肯定采用shuffle

val newRDD : RDD[Int] = rdd.repartition(3)
newRDD.saveAsTextFile("output")

~~~

