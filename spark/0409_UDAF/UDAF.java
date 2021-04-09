

    public static Column merge(String col, StructType type) {

        return new UserDefinedAggregateFunction() {
            @Override
            public StructType inputSchema() {
                return new StructType()
                        .add("a", type);
            }

            @Override
            public StructType bufferSchema() {
                return type;
            }

            @Override
            public DataType dataType() {
                return type;
            }

            @Override
            public boolean deterministic() {
                return false;
            }

            @Override
            public void initialize(MutableAggregationBuffer buffer) {

                // 这里先构建一个 [null, null]的初始化结构体
                // 注意此处的buffer从初始化开始 就会贯穿整个函数 -> update() -> merge() -> evaluate()
                for (int i = 0; i < buffer.length(); i++) {
                    buffer.update(i, null);
                }
            }

            @Override
            public void update(MutableAggregationBuffer buffer, Row input) {    //input是一个[[a1, b1]]

                //row取第一个结构体[a1, b1]
                Row row = input.getAs(0);   

                //row有多少列, 就和buffer比较多少次
                for (int i = 0; i < row.length(); i++) {    
                    Object tempInput = row.get(i);
                    if(tempInput != null){
                        buffer.update(i, tempInput);
                    }
                }
            }

            @Override
            public void merge(MutableAggregationBuffer buffer1, Row buffer2) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object evaluate(Row buffer) {
                return buffer;
            }
        }.apply(expr(col));
    }





    @Test
    public void mergeTest1() {
        Dataset<Row> ds = spark.createDataset(ImmutableList.of(
                Tuple2.apply("a1", 1),
                Tuple2.apply("a2", null),
                Tuple2.apply(null, 2)
        ), Encoders.tuple(Encoders.STRING(), Encoders.INT())).toDF()
                .selectExpr("struct(_1,_2) a")
                .withColumn("id", monotonically_increasing_id());

        List<Row> result = ds
                .select(merge("a", ds.select("a.*").schema()).over(Window.orderBy("id")).as("a"))
                .selectExpr("a.*")
                .collectAsList();
        ds.show();
        Assert.assertEquals("a2", result.get(1).apply(0));
        Assert.assertEquals(1, result.get(1).apply(1));
        Assert.assertEquals("a2", result.get(2).apply(0));
        Assert.assertEquals(2, result.get(2).apply(1));
    }