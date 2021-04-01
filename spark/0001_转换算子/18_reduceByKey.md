reduceByKey

~~~scala
val rdd = sc.makeRDD(List(
	("a", 1), ("a", 2), ("a", 3), ("b", 4)
))

//reduceByKey: 相同的key的数据进行value数据的聚合操作
//两两聚合
//结果  a->(6)     b->(4)

val reduceRDD: RDD[(String, Int)] = rdd.reduceByKey( (x:Int, y:Int) => {x+y} )
reduceRDD.collect().foreach(println)
~~~









