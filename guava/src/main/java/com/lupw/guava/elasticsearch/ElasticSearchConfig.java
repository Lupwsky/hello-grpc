package com.lupw.guava.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * @author v_pwlu 2019/4/23
 */
@Configurable
public class ElasticSearchConfig {

    @Bean
    public RestHighLevelClient elasticSearchClient() {
        return new RestHighLevelClient(RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
    }
}
