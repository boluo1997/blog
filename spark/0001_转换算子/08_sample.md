sample

根据指定的规则从数据集中抽取数据



~~~scala
val rdd = sc.makeRDD(List(1,2,3,4,5,6,7,8,9,10))

//sample算子需要传递三个参数
//1.第一个参数表示, 抽取数据后是否将数据返回 true(放回), false(丢弃)
//2.第二个参数表示, 数据源中每条数据被抽取的概率
//3.第三个参数表示, 抽取数据时随机算法的种子

println(rdd.sample(
	false,
    0.4,
    1
).collect().mkString(","))

~~~