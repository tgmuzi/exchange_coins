package com.zeus.utils;

import okhttp3.*;
import org.apache.commons.codec.Charsets;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class HttpUtils {
    protected static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    // 发送POST请求
    public static String postRequest(String url, String parame)  {
        OkHttpClient client = new OkHttpClient();
        try {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, parame);
            // 1 创建OkHttpClient对象
            // 2 构建Request对象
            Request request = new Request.Builder()
                    .get()// 不写默认为GET请求
                    .url(url)
                    .post(body)
                    .addHeader("accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .build();
            // 3 发起请求获取响应值
            Response response = client.newCall(request).execute();
            // 4 根据响应结果判断
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new RuntimeException("请求异常,错误码为: " + response.code());
            }
        } catch (Exception e) {
            logger.info("请求失败,错误信息为= {} ", e.getMessage());
        }
        return null;
    }
    /**
     * GET提交数据方法
     * @author temdy
     * @Date 2016-08-26
     * @param postUrl POST提交URL
     * @return String
     */
    public static String get(String postUrl) {
        String obj = null;
        org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
        GetMethod method = null;
        try {
            method = new GetMethod(postUrl);
            client.executeMethod(method);
            client.getHttpConnectionManager().getParams().setConnectionTimeout(8000);
            client.getHttpConnectionManager().getParams().setSoTimeout(8000);
            obj = method.getResponseBodyAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
    // 发送POST请求（普通表单形式）
    public static String postForm(String path, List<NameValuePair> parametersBody) throws Exception {
        HttpEntity entity = new UrlEncodedFormEntity(parametersBody, Charsets.UTF_8);
        return postRequest(path, "application/x-www-form-urlencoded", entity);
    }

    // 发送POST请求（JSON形式）
    public static String postJSON(String path, String json) throws Exception {
        StringEntity entity = new StringEntity(json, Charsets.UTF_8);
        return postRequest(path, "application/json", entity);
    }

    // 发送POST请求
    public static String postRequest(String path, String mediaType, HttpEntity entity) throws Exception {
        logger.debug("[postRequest] resourceUrl: {}", path);
        HttpPost post = new HttpPost(path);
        post.addHeader("Content-Type", mediaType);
        post.addHeader("Accept", "application/json");
        post.setEntity(entity);
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(post);
            int code = response.getStatusLine().getStatusCode();
            if (code >= 400)
                throw new Exception(EntityUtils.toString(response.getEntity()));
            return EntityUtils.toString(response.getEntity());
        }
        catch (ClientProtocolException e) {
            throw new Exception("postRequest -- Client protocol exception!", e);
        }
        catch (IOException e) {
            throw new Exception("postRequest -- IO error!", e);
        }
        finally {
            post.releaseConnection();
        }
    }


}
