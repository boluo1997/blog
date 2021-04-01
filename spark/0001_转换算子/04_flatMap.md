flatMap：扁平映射

~~~scala
val rdd: RDD[List[Int]] = sc.makeRDD(List(
	List(1,2), List(3,4)	
))

val flatRDD: RDD[Int] = rdd.flatMap(
	list => {
        list
    }
)

flatRDD.collect().foreach(println)

~~~

~~~scala
// word count
val rdd: RDD[String] = sc.makeRDD(List(
	"Hello Spark", "Hello Scala"	
))

val flatRDD: RDD[String] = rdd.flatMap(
	s => {
        s.split(" ")
    }
)

flatRDD.collect().foreach(println)
~~~

~~~scala
val rdd: RDD[List[Int]] = sc.makeRDD(List(
	List(1,2), 3, List(3,4)	
))

val flatRDD = rdd.flatMap(
	data => {
        data match {
            case list:List[_] => list
            case dat => List(dat)
        }
    }
)

~~~

