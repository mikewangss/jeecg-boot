package org.jeecg.modules.demo.settlement.util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
@Configuration
public class HttpGetAndPost {

    /**
     * 通用post请求封装
     * @param url
     * @param map
     * @return
     */
    public String httpPost(String url, HashMap map){
        String json = JSON.toJSONString(map);//map转String
        JSONObject jsonData = JSON.parseObject(json);//String转json
        RestTemplate restTemplate = new RestTemplate();
        //设置Http Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
        headers.add("Accept","application/json, text/plain, */*");
        //设置请求媒体数据类型
        headers.setContentType(MediaType.APPLICATION_JSON);
        //设置返回媒体数据类型
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(jsonData.toString(), headers);
        return restTemplate.postForObject(url, formEntity, String.class);
    }

    /**
     * 通用get请求
     * @param url
     * @return
     */
    public Result httpGet(String url){
        RestTemplate restTemplate = new RestTemplate();
        //设置Http Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
        headers.add("Accept","application/json, text/plain, */*");
        //设置请求媒体数据类型
        headers.setContentType(MediaType.APPLICATION_JSON);
        //设置返回媒体数据类型
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        return restTemplate.getForObject(url, Result.class);
    }

    /**
     * 自定义请求头的post请求
     * @param url
     * @param jsonData
     * @param jsonHeaders
     * @return
     */
    public String httpPost(String url, JSONObject jsonData,JSONObject jsonHeaders){
        RestTemplate restTemplate = new RestTemplate();
        //设置Http Header
        HttpHeaders headers = getHeaders(jsonHeaders);
        //设置请求媒体数据类型
        headers.setContentType(MediaType.APPLICATION_JSON);
        //设置返回媒体数据类型
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(jsonData.toString(), headers);
        return restTemplate.postForObject(url, formEntity, String.class);
    }


    /**
     * 自定义请求头的get请求
     * @param url
     * @param jsonHeaders
     * @return
     */
    public String httpGet(String url,JSONObject jsonHeaders){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHeaders(jsonHeaders);
        HttpEntity request = new HttpEntity(headers);
        // 构造execute()执行所需要的参数。
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, JSONObject.class);
        ResponseExtractor<ResponseEntity<JSONObject>> responseExtractor = restTemplate.responseEntityExtractor(JSONObject.class);
        // 执行execute()，发送请求
        return   Objects.requireNonNull(Objects.requireNonNull(restTemplate.execute(url, HttpMethod.GET, requestCallback, responseExtractor)).getBody()).toString();
    }




    /**
     * json转化请求头
     * @param jsonHeaders
     * @return
     */
    public static HttpHeaders getHeaders(JSONObject jsonHeaders){
        Iterator iterator = jsonHeaders.entrySet().iterator();
        HttpHeaders headers = new HttpHeaders();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            headers.add(entry.getKey().toString(),entry.getValue().toString());
        }
        return headers;
    }


}