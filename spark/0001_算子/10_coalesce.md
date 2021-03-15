coalesce

根据数据量缩减分区，用于大数据集过滤后，提高小数据集的执行效率

当spark程序中，存在过多的小任务的时候，可以通过coalesce方法，收缩合并分区，减少分区的个数，减小任务调度成本



~~~scala
val rdd = sc.makeRDD(List(1,2,3,4,5,6), 3)
//coalesce方法默认不会将分区的数据打乱重新组合
val newRDD : RDD[Int] = rdd.coalesce(2)

newRDD.saveAsTestFile("output")
//重新分完区之后是1,2   3,4,5,6
//所以这种情况下的缩减分区可能会导致数据不均衡, 出现数据倾斜
//如果想要数据均衡, 可以进行shuffle处理
val newRDD : RDD[Int] = rdd.coalesce(2, true)

~~~

