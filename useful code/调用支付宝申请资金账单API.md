~~~java
package com.boluo.hatchery.arch;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableList;
import com.google.common.io.ByteStreams;
import com.landeli.spark.Sparks;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.parquet.Strings;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/*
-m=2021
-pub=MII
-pre=MIIEvg
-t=file:///d:/data/down_ali
*/
public class FromAlipayBillsV2 {

    private static final Logger logger = LoggerFactory.getLogger(FromAlipayBills.class);
    private static final Pattern filenamePattern2 = Pattern.compile("(\\d+)_(20\\d{2}\\d{2}\\d{2})_业务明细.csv");
    private static final DateTimeFormatter ymdFormater = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static void main(String[] args) throws Exception {

        CommandLine cli = new GnuParser().parse(new Options()
                .addOption("m", "appId", true, "")
                .addOption("pub", "publicKey", true, "")
                .addOption("pre", "privateKey", true, "")
                .addOption("t", "target", true, ""), args);

        String target = CharMatcher.anyOf("/").trimTrailingFrom(cli.getOptionValue("t"));
        String appId = cli.getOptionValue("m");
        String publicKey = cli.getOptionValue("pub");
        String privateKey = cli.getOptionValue("pre");

        // spark初始化
        SparkSession spark = Sparks.builder("from_alipay_bills")
                .config(Sparks.fsConf(target))
                .getOrCreate();
        Path parent = Sparks.toPath(target);
        FileSystem fs = FileSystem.get(parent.toUri(), spark.sparkContext().hadoopConfiguration());
        LocalDate lastDate = Stream.of(fs.listStatus(parent))
                .map(file -> {
                    Matcher matcher;
                    if ((matcher = filenamePattern2.matcher(file.getPath().getName())).matches()) {
                        return LocalDate.parse(matcher.group(2), ymdFormater).plusDays(1);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .max(LocalDate::compareTo)
                .orElse(LocalDate.of(2021, 3, 1));

        String url;
        for (LocalDate date = lastDate; date.isBefore(LocalDate.now()); date = date.plusDays(1)) {
            url = billUrl(date.toString(), appId, publicKey, privateKey);
            if (!Strings.isNullOrEmpty(url)) {
                downloadUrl(url, fs, parent);
                Thread.sleep(1000);
            }
        }
    }

    // "\"bill_date\":\"2021-03-14\"" +
    public static String billUrl(String date, String appId, String publicKey, String privateKey) throws Exception {

        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, privateKey, "json", "GBK", publicKey, "RSA2");
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        request.setBizContent("{" +
                "\"bill_type\":\"trade\"," +
                "\"bill_date\":\"" + date + "\"" +
                "  }");

        AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
        if (response.getCode().equals("10000")) {
            return response.getBillDownloadUrl();
        } else if (response.getCode().equals("40004")) {
            logger.info(date.toString() + " bill_not_exist ^_^");
            return null;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static void downloadUrl(String url, FileSystem fs, Path parent) throws IOException {
        CloseableHttpClient http = HttpClients.createDefault();
        HttpGet reqBill = new HttpGet(url);
        CloseableHttpResponse res = http.execute(reqBill);

        if (res.getStatusLine().getStatusCode() == 200) {
            try (ZipInputStream zip = new ZipInputStream(res.getEntity().getContent(), Charset.forName("GB2312"))) {
                ZipEntry file;
                while ((file = zip.getNextEntry()) != null) {
                    Matcher m;
                    if ((m = filenamePattern2.matcher(file.getName())).matches()) {
                        try (FSDataOutputStream output = fs.create(new Path(parent, file.getName()))) {
                            ByteStreams.copy(zip, output);
                            output.hsync();
                            System.out.println("fileName: " + file.getName());
                        }
                        break;
                    }
                }
            }
        }
    }

}

~~~

