distinct

~~~scala
val rdd = sc.makeRDD(List(1,2,3,4,1,2,3,4))

val rdd1:RDD[Int] = rdd.distinct
rdd1.collect().foreach(println)

//distinct真正的逻辑
//map(x => (x, null)).reduceByKey((x, _) => x, numParttions).map(_._1)

~~~

