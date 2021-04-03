- 从内存中创建RDD

~~~scala
val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
val sc = new SparkContext(sparkConf)

//从内存中创建RDD, 将内存中集合的数据作为处理的数据源
val seq = Seq[Int](1,2,3,4)

// parallelize : 并行
// val rdd : RDD[Int] = sc.parallelize(seq)
//makeRDD底层调用了parallelize()
val rdd : RDD[Int] = sc.makeRDD(seq)

rdd.collect().foreach(println)

sc.stop()

~~~



- 从文件中创建RDD

~~~scala
val sc = new SparkContext(sparkConf)

//从文件中创建RDD, 将文件中的数据作为处理的数据源
//path路径默认以当前环境的根路径为基准. 可以写绝对路径, 也可以写相对路径
sc.textFile("D:\\projects\\idea\\classes\\atguigu\\datas\\datas\\1.txt")
val rdd : RDD[String] = sc.textFile("datas/1.txt")

//path路径可以是文件的具体路径, 也可以是目录名称
val rdd = sc.textFile("datas")

//path路径还可以使用通配符
val rdd = sc.textFile("datas/1*.txt")

//path还可以是分布式存储系统路径: HDFS
val rdd = sc.textFile("hdfs://linux1:8020/text.txt")
rdd.collect().foreach(println)

sc.stop()
~~~



~~~scala

// textFile: 以行为单位来读取数据, 读取的数据都是字符串
// wholeTextFiles: 以文件为单位读取数据
// 读取的结果表示为元组, 第一个元素表示文件路径, 第二个元素表示文件内容

val rdd = sc.wholeTextFile("datas")

rdd.collect().foreach(println)
// (file:/D... , Hello World)
~~~