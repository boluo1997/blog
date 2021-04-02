foreach算子

~~~scala
val rdd = sc.makeRDD(List(1,2,3,4))

//先collect()再foreach() 打印顺序是1234 是Driver端内存集合的循环遍历方法
rdd.collect().foreach(println)

//直接foreach() 打印顺序是随机的2134 是Executor端内存数据打印
rdd.foreach(println)

// 算子: Operator(操作)
// RDD的方法和scala集合对象的方法不一样
// 集合对象的方法都是在同一节点的内存中完成的
// RDD的方法可以将计算逻辑发送到Executor端(分布式节点)执行
// 为了区分不同的处理效果, 所以将RDD的方法称为算子
// RDD的方法外部的操作都是在Driver端执行的, 而方法内部的逻辑代码是在Executor端执行的
~~~


