package com.sky.utils;

import com.alibaba.fastjson2.JSONObject;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.EntityBuilder;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.util.Timeout;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    static final Timeout TIMEOUT = Timeout.ofSeconds(5);

    public static String doGet(String url, Map<String, String> paramMap) {
        String result = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URIBuilder builder = new URIBuilder(url);
            if(paramMap != null){
                paramMap.forEach(builder::addParameter);
            }
            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);

            try (ClassicHttpResponse response = httpClient.executeOpen(null, httpGet, null)) {
                if(response.getCode() == HttpStatus.SC_OK){
                    result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String doPost(String url, Map<String, String> paramMap) {
        String result = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            if(paramMap != null){
                List<NameValuePair> paramList = new ArrayList<>();
                paramMap.forEach((k,v)->{
                    paramList.add(new BasicNameValuePair(k, v));
                });
                try (UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList)) {
                    httpPost.setEntity(entity);
                }
            }

            httpPost.setConfig(builderRequestConfig());

            try (ClassicHttpResponse response = httpClient.executeOpen(null, httpPost, null)) {
                result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String doPost4Json(String url, Map<String, String> paramMap) {
        String result = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            if(paramMap != null){
                JSONObject jsonObject = new JSONObject();
                jsonObject.putAll(paramMap);
                try (HttpEntity entity = EntityBuilder.create()
                        .setContentEncoding(StandardCharsets.UTF_8.name())
                        .setContentType(ContentType.APPLICATION_JSON)
                        .build()) {
                    httpPost.setEntity(entity);
                }
            }

            httpPost.setConfig(builderRequestConfig());

            try (ClassicHttpResponse response = httpClient.executeOpen(null, httpPost, null)) {
                result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static RequestConfig builderRequestConfig() {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(TIMEOUT)
                .setResponseTimeout(TIMEOUT)
                .setConnectionRequestTimeout(TIMEOUT)
                .build();
    }
}
