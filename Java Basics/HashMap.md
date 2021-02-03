## 1.简单说一下你对HashMap的理解

1.结构：HashMap的底层结构为 **数组+链表**，即HashMap整体是个数组，数组中的每个位置都是一个链表，我们的数据就存放在这些链表的value处。

2.工作原理：先计算出对象的hash值，用计算出的hash值去调用indexFor()方法判断该对象应该存在几号桶中，存入之前先判断hashmap中的元素个数是否达到了当前的阈值，如果到达了阈值，扩容；没有到达阈值的话，存入。

3.扩容机制：先将数组长度扩容到原来的两倍，再将原来数组中的元素重新放回（因为元素位置会发生改变）



## 2.说一下HashMap和HashTable的区别

1.HashMap和HashTable的结构和解决冲突的方法相同

2.HashMap线程不安全，效率高。HashTable线程安全，效率低。

3.HashMap默认大小是16，HashTable默认大小11，HashTable不要求底层数组大小为2的整数次幂，HashMap要求底层数组大小是2的整数次幂。

4.扩容时，HashMap变成原来的2倍，HashTable变为原来的2倍+1.

5.HashTable中value和key都不允许出现null值。HashMap中key和value可以为null



## 3.HashTable和ConcurrentHashMap的区别

HashTable线程安全是因为给每个方法都加了synchronized，concurrentHashMap是如何实现线程安全的自己百度。

