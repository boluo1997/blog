

调用微信申请资金账单API

![](../$image/Snipaste_2021-03-18_22-24-52.png)

~~~java
<dependency>
	<groupId>com.github.wechatpay-apiv3</groupId>
	<artifactId>wechatpay-apache-httpclient</artifactId>
	<version>0.2.1</version>
</dependency>
~~~



~~~java
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class boluoTest6 {
    private static String mchId = "商户号";
    private static String mchSerialNo = "商户证书序列号";
    private static String apiV3Key = "api密钥";

    private CloseableHttpClient httpClient;
    private AutoUpdateCertificatesVerifier verifier;

    private static String reqdata = "{\n"
            + "    \"stock_id\": \"9433645\",\n"
            + "    \"stock_creator_mchid\": \"1900006511\",\n"
            + "    \"out_request_no\": \"20190522_001中文11\",\n"
            + "    \"appid\": \"wxab8acb865bb1637e\"\n"
            + "}";

    // 你的商户私钥
    private static String privateKey = "-----BEGIN PRIVATE KEY-----\n" +

            + "-----END PRIVATE KEY-----\n";

 
    @Before
    public void setup() throws IOException {
        Security.setProperty("crypto.policy", "unlimited");
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(
                new ByteArrayInputStream(privateKey.getBytes("utf-8")));
        X509Certificate wechatpayCertificate = PemUtil.loadCertificate(
                new ByteArrayInputStream(certificate.getBytes("utf-8")));

        ArrayList<X509Certificate> listCertificates = new ArrayList<>();
        listCertificates.add(wechatpayCertificate);

        //使用自动更新的签名验证器，不需要传入证书
        verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials(mchId, new PrivateKeySigner(mchSerialNo, merchantPrivateKey)),
                apiV3Key.getBytes("utf-8"));

        httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier))
                .build();
    }

    @After
    public void after() throws IOException {
        httpClient.close();
    }


    @Test
    public void test11() throws IOException {
        HttpGet httpGet = new HttpGet(
                "https://api.mch.weixin.qq.com/v3/bill/fundflowbill?bill_date=2021-03-15&account_type=BASIC");
        httpGet.addHeader("Accept", "application/json");


        CloseableHttpResponse response = httpClient.execute(httpGet);
        //assertTrue(response.getStatusLine().getStatusCode() != 401);
        try {
            HttpEntity entity2 = response.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            System.out.println(EntityUtils.toString(entity2));

            EntityUtils.consume(entity2);
        } finally {
            response.close();
        }

        httpGet = new HttpGet(
                "https://api.mch.weixin.qq.com/v3/bill/fundflowbill?bill_date=2021-03-15&account_type=BASIC");
        response = httpClient.execute(httpGet);
        //assertTrue(response.getStatusLine().getStatusCode() != 401);
        try {
            HttpEntity entity2 = response.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            System.out.println(EntityUtils.toString(entity2));
            ;
            EntityUtils.consume(entity2);
        } finally {
            response.close();
        }
    }

}




~~~

