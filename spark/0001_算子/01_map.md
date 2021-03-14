算子：RDD方法

算子分为 转换算子和行动算子

转换算子：从旧的RDD -> 新的RDD



RDD的计算对一个分区内的数据一条一条执行

- 分区内的数据执行是有序的
- 不同分区的数据计算是无序的



map()：将处理的数据逐条进行映射转换，可以是类型的转换，也可以是值的转换



~~~java
Dataset df2 = df1.map(new MapFunction<Row, BoLuo>() {
            @Override
            public BoLuo call(Row row) throws Exception {

                String seq = row.getAs("订单号") + ":T";
                String no = row.getAs("订单号");

                BoLuo result = new BoLuo();
                result.setSeq(seq);
                result.setNo(no);

                return result;
            }
        }, Encoders.bean(BoLuo.class));
~~~



