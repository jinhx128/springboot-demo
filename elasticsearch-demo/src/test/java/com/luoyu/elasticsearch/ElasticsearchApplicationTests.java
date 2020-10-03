package com.luoyu.elasticsearch;

import com.luoyu.elasticsearch.entity.User;
import com.luoyu.elasticsearch.service.ElasticSearchService;
import com.luoyu.elasticsearch.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class ElasticsearchApplicationTests {

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Test
    void createIndexTest() throws Exception {
        // 创建索引
        elasticSearchService.createIndex("test_index");
    }

    @Test
    void existIndexTest() throws Exception {
        // 判断索引是否存在
        elasticSearchService.existIndex("test_index");
    }

    @Test
    void deleteIndexTest() throws Exception {
        // 删除索引
        elasticSearchService.deleteIndex("test_index");
    }

    @Test
    void addDocumentTest() throws Exception {
        // 新增文档
        User user = new User();
        user.setId(1L);
        user.setAge(12);
        user.setName("测试name");
        user.setDescription("测试des");

        elasticSearchService.addDocument("test_index", user.getId().toString(), JsonUtils.objectToJson(user));
    }

    @Test
    void isExistsDocumentTest() throws Exception {
        // 判断是否存在文档
        elasticSearchService.isExistsDocument("test_index", "1");
    }

    @Test
    void getDocumentTest() throws Exception {
        // 获取文档
        elasticSearchService.getDocument("test_index", "1");

    }

    @Test
    void updateDocumentTest() throws Exception {
        // 更新文档
        User user = new User();
        user.setId(1L);
        user.setAge(33);
        user.setName("测试name");
        user.setDescription("测试des");

        elasticSearchService.updateDocument("test_index", user.getId().toString(), JsonUtils.objectToJson(user));
    }

    @Test
    void deleteDocumentTest() throws Exception {
        // 删除文档
        elasticSearchService.deleteDocument("test_index", "1");
    }

    @Test
    void bulkRequestTest() throws Exception {
        // 批量插入
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setId((long) i);
            user.setAge(i);
            user.setName("测试name" + i);
            user.setDescription("测试des" + i);
            users.add(user);
        }

        elasticSearchService.bulkRequest("test_index", users);
    }

    @Test
    void searchRequestTest() throws Exception {
        // 搜索请求
        elasticSearchService.searchRequest("test_index", "测试");
    }

    @Test
    void searchAllRequestTest() throws Exception {
        // 搜索请求
        elasticSearchService.searchAllRequest("test_index");
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
