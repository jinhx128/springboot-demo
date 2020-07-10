package com.luoyu.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/7/8 4:23 下午
 * @Version: 1.0.0
 */
@Configuration
public class ElasticSearchClientConfig {

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("172.16.31.191", 9200, "http"),
                    new HttpHost("172.16.31.192", 9200, "http"),
                    new HttpHost("172.16.31.193", 9200, "http")
            ));
        return restHighLevelClient;
    }

}
