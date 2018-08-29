package com.vwfsag.fso.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.vwfsag.fso.service.EncryptServiceImpl;

/**
 * @author qngl
 *
 */
public class HttpClientTest {


    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpclient = HttpClients.custom()
                .build();
        try {
        	EncryptServiceImpl enc = new EncryptServiceImpl();
        	String key = "1234567891234567";
            HttpPost httpget = new HttpPost("http://localhost:8080/WeChatConnector/synch/fetch");
            String param = "{\"couponCode\" : \"301454601102\", \"status\" : 4}";
            List<BasicNameValuePair> values = new ArrayList<BasicNameValuePair>();
            values.add(new BasicNameValuePair("data", enc.aesEncrypt(param, key)));
            UrlEncodedFormEntity data = new UrlEncodedFormEntity(values);
            httpget.setEntity(data);

            System.out.println("Executing request " + httpget.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                String res = EntityUtils.toString(response.getEntity());
                System.out.println(enc.aesDecrypt(res, key));
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
	
}
