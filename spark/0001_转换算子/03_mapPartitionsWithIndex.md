给mapPartitions加上下标索引



~~~scala
val rdd = sc.makeRDD(List(1,2,3,4),2)

//只取第二个分区的数据
val mpiRDD = rdd.mapPartitionsWithIndex(
	(index, iter) => {
        if(index == 1){
            iter
        } else {
            Nil.iterator
        }
    }
)

mpiRDD.collect().foreach(println)


~~~



~~~scala
val rdd = sc.makeRDD(List(1,2,3,4),2)

//查看每个数据所在的分区
val mpiRDD = rdd.mapPartitionsWithIndex(
	(index, iter) => {
        iter.map(
        	num => {
                (index, num)
            }
        )
    }
)

mpiRDD.collect().foreach(println)
~~~

