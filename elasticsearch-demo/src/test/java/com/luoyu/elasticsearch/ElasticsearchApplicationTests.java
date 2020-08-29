package com.luoyu.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.luoyu.elasticsearch.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class ElasticsearchApplicationTests {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Test
    void testCreateIndex() throws Exception {
        // 创建索引
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("test_index");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    @Test
    void testExistIndex() throws Exception {
        // 判断索引是否存在
        GetIndexRequest getIndexRequest = new GetIndexRequest("test_index");
        boolean exist = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(exist);
    }

    @Test
    void testDeleteIndex() throws Exception {
        // 判断索引是否存在
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("test_index");
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(acknowledgedResponse.isAcknowledged());
    }

    @Test
    void testAddDocument() throws Exception {
        // 新增文档
        User user = new User();
        user.setAge(12);
        user.setName("ff落333雨33");

        IndexRequest indexRequest = new IndexRequest("test_index");
        // 设置超时时间
        indexRequest.id("4");
        indexRequest.timeout(TimeValue.timeValueSeconds(1));
        // 转换为json字符串
        indexRequest.source(JSON.toJSONString(user), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.toString());
    }

    @Test
    void testIsExistsDocument() throws Exception {
        // 判断是否存在文档
        GetRequest getRequest = new GetRequest("test_index", "1");
        // 不获取返回的_source的上下文
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    @Test
    void testGetDocument() throws Exception {
        // 获取文档
        GetRequest getRequest = new GetRequest("test_index", "1");
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse.getSourceAsString());
    }

    @Test
    void testUpdateDocument() throws Exception {
        // 更新文档
        User user = new User();
        user.setAge(22);
        user.setName("落雨");

        UpdateRequest updateRequest = new UpdateRequest("test_index", "1");
        updateRequest.timeout(TimeValue.timeValueSeconds(1));
        updateRequest.doc(JSON.toJSONString(user), XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(updateResponse.status());
    }

    @Test
    void testDeleteDocument() throws Exception {
        // 删除文档
        DeleteRequest deleteRequest = new DeleteRequest("test_index", "1");
        deleteRequest.timeout(TimeValue.timeValueSeconds(1));
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.status());
    }

    @Test
    void testBulkRequest() throws Exception {
        // 批量插入
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout(TimeValue.timeValueSeconds(1));
        for (int i = 0; i < 10 ; i++){
            User user = new User();
            user.setAge(10 + i);
            user.setName("落雨" + i);

            bulkRequest.add(
                    new IndexRequest("test_index")
                    .id("" + (i + 100))
                    .source(JSON.toJSONString(user), XContentType.JSON));
        }
        BulkResponse bulkItemResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkItemResponse.hasFailures());
    }

    @Test
    void testSearchRequest() throws Exception {
        // 搜索请求
        SearchRequest searchRequest = new SearchRequest("test_index");
        // 条件构造
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 构造高亮
        searchSourceBuilder.highlighter();
        // 精确查询
//        QueryBuilders.termQuery();
        // 匹配所有
//        QueryBuilders.matchAllQuery();

        // 精确查询
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "落雨");
//        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        searchSourceBuilder.query(termQueryBuilder);
        searchSourceBuilder.timeout(TimeValue.timeValueSeconds(60));

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        searchResponse.getHits().forEach(x -> {
            System.out.println(x.getSourceAsMap());
        });
    }

    @BeforeEach
    void testBefore(){
        log.info("测试开始!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @AfterEach
    void testAfter(){
        log.info("测试结束!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
