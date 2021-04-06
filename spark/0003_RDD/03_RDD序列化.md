在网络中传输数据需要将数据序列化

Java的序列化可以序列化任何的类, 但是比较重 ( 字节多 ) , 序列化后, 对象的提交也比较大. 

Spark处于性能的考虑, 开始支持Kryo序列化机制. Kryo速度是Serializable的10倍. 当RDD在shuffle数据的时候, 简单数据类型、数组和字符串类型已经在Spark内部使用Kryo来序列化

即使使用Kryo序列化, 也要继承Serializable接口