import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.spark.sql.*;
import org.apache.spark.sql.expressions.UserDefinedFunction;
import org.apache.spark.sql.types.StructType;


public class boluoTest4 {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
 		
 		String orderInfo = "D:\\data\\haier\\order";
    	
    	SparkSession spark = SparkSession
                .builder()
                .master("local[*]")
                .appName("Simple Application")
                .getOrCreate();

        //这里如果不加format("delta")的话, 路径那里可以写成"D:\\data\\haier\\order\\date=2019-08-22"
        Dataset<Row> df1 = spark.read().format("delta").load(orderInfo);
        df1.printSchema();
        df1.show(false);

        //第一版
        //此处BoLuo为自定义类, 接收处理完的数据, 返回值类型
        Dataset df2 = df1.map(new MapFunction<Row, BoLuo>(){
        	
        	@Override
        	public BoLuo call(Row row) throws Exception{

        		String seq = row.getAs("订单号") + ":T";
                String no = row.getAs("订单号");
                String time = row.getAs("订单时间");

                BoLuo result = new BoLuo();
                result.setSeq(seq);
                result.setTime(time);
                result.setNo(no);

                return result;
        		
        	}
        }, Encoders.bean(BoLuo.class));


        //第二版
        //此处返回一列, 需要在返回值类型处定义这一列
        Dataset df2 = df1.map(new MapFunction<Row, Row>(){
        	
        	String seq = row.getAs("订单号") + ":T";
            String no = row.getAs("订单号");
            String time = row.getAs("订单时间");

            Row order = RowFactory.create(seq, no, time, null);
            return order;

        }, RowEncoder.apply(new StructType()
        		.add("流水号", "string")
        		.add("订单号", "string")
        		.add("订单时间", "string")));


        //第三版
        
        
    }

}