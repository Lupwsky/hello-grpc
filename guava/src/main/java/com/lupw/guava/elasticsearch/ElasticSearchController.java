package com.lupw.guava.elasticsearch;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author v_pwlu 2019/4/23
 */
@Slf4j
@RestController
public class ElasticSearchController {

    private final RestHighLevelClient client;

    @Autowired
    public ElasticSearchController(RestHighLevelClient client) {
        this.client = client;
    }

    // (0) 基础概念 = https://xiangzhurui.com/2018/03/07/Elasticsearch-%E5%85%A5%E9%97%A8-%E5%9F%BA%E7%A1%80%E6%A6%82%E5%BF%B5/
    // (1) Index 和 Type 的区别 = http://bayescafe.com/database/elasticsearch-using-index-or-type.html

    @GetMapping("/elasticsearch/create/index/test")
    public void createIndexTest() {
        Map<String, Object> jsonMap = Maps.newHashMap();
        jsonMap.put("name", "lpw");
        jsonMap.put("password", "test");

        IndexRequest indexRequest = new IndexRequest("user_data", "user_info", "1");
        indexRequest.source(jsonMap);

        try {
            client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/elasticsearch/get/request/test")
    public void getRequestTest() {
        GetRequest getRequest = new GetRequest("user_data", "user_info", "1");
        try {
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            log.info("index = {}", getResponse.getIndex());
            log.info("type = {}", getResponse.getType());
            log.info("id = {}", getResponse.getId());
            log.info("source = {}", getResponse.getSource());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
