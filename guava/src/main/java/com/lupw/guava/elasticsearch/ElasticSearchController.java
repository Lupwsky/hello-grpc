package com.lupw.guava.elasticsearch;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.get.GetResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * @author v_pwlu 2019/4/23
 */
@Slf4j
@RestController
public class ElasticSearchController {

    private final static String ES_INDEX = "user_data";
    private final static String ES_TYPE = "user_info";
    private final RestHighLevelClient client;

    @Autowired
    public ElasticSearchController(RestHighLevelClient client) {
        this.client = client;
    }

    /**
     * 官方文档 = https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.6/java-rest-high-document-index.html
     * Index 和 Type 的区别 = http://bayescafe.com/database/elasticsearch-using-index-or-type.html
     */
    @GetMapping("/elasticsearch/create/index/test")
    public void createIndexTest() {
        Map<String, Object> jsonMap = Maps.newHashMap();
        jsonMap.put("name", "lpw");
        jsonMap.put("password", "test");

        IndexRequest indexRequest = new IndexRequest(ES_INDEX, ES_TYPE, "1");
        indexRequest.source(jsonMap);

        try {
            client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 官方文档 = https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.6/java-rest-high-document-get.html
     */
    @GetMapping("/elasticsearch/get/request/test")
    public void getRequestTest() {
        GetRequest getRequest = new GetRequest(ES_INDEX, ES_TYPE, "1");
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

    /**
     * 官方文档 = https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.6/java-rest-high-document-exist.html
     */
    @GetMapping("/elasticsearch/exist/request/test")
    public void existRequestTest() {
        GetRequest getRequest = new GetRequest(ES_INDEX, ES_TYPE, "1");
        try {
            boolean isExist = client.exists(getRequest, RequestOptions.DEFAULT);
            log.info("isExist = {}", isExist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 官方文档 = https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.6/java-rest-high-document-delete.html
     */
    @GetMapping("/elasticsearch/delete/request/test")
    public void deleteRequestTest() {
        DeleteRequest deleteRequest = new DeleteRequest(ES_INDEX, ES_TYPE, "2");
        try {
            DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
            log.info("deleteStatus = {}", deleteResponse.status().getStatus());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 官方文档 = https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.6/java-rest-high-document-update.html
     */
    @GetMapping("/elasticsearch/update/request/test")
    public void updateRequestTest() {
        UpdateRequest updateRequest = new UpdateRequest(ES_INDEX, ES_TYPE, "1");
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.field("name", "lpw123");
                builder.field("password", "lpw123");
            }
            builder.endObject();

            UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
            GetResult getResult = updateResponse.getGetResult();
            if (getResult.isExists()) {
                log.info("source = {}", getResult.getSource());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能 = 返回特定文档字段中术语的信息和统计信息
     * 官方文档 = https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.6/java-rest-high-document-term-vectors.html
     */
    @GetMapping("/elasticsearch/term/request/test")
    public void termVectorsRequestTest() {
        TermVectorsRequest termVectorsRequest = new TermVectorsRequest(ES_INDEX, ES_TYPE, "1");
        termVectorsRequest.setFields("name", "password");
    }
}