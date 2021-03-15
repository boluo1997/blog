sortBy

根据指定的规则对数据进行排序



~~~scala
val rdd = sc.makeRDD(List(6,2,4,5,3,1), 2)

val sortRDD: RDD[Int] =  rdd.sortBy(num => num)

sortRDD.saveAsTextFile("output")


val rdd = sc.makeRDD(List(("1", 1)("11", 2)("2", 3)), 2)
val newRDD = rdd.sortBy(t => t._1.toInt)
newRDD.collect().foreach(println)
~~~

