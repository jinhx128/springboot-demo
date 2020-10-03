package com.luoyu.elasticsearch.service;

import com.luoyu.elasticsearch.entity.User;

import java.util.List;
import java.util.Map;

public interface ElasticSearchService {

    /**
     * 创建索引
     * @param index
     */
    boolean createIndex(String index) throws Exception;

    /**
     * 判断索引是否存在
     * @param index
     */
    boolean existIndex(String index) throws Exception;

    /**
     * 删除索引
     * @param index
     */
    boolean deleteIndex(String index) throws Exception;

    /**
     * 新增文档
     * @param index
     * @param id
     * @param content
     */
    boolean addDocument(String index, String id, String content) throws Exception;

    /**
     * 判断是否存在文档
     * @param index
     * @param id
     */
    boolean isExistsDocument(String index, String id) throws Exception;

    /**
     * 获取文档
     * @param index
     * @param id
     */
    String getDocument(String index, String id) throws Exception;

    /**
     * 更新文档
     * @param index
     * @param id
     * @param content
     */
    boolean updateDocument(String index, String id, String content) throws Exception;

    /**
     * 删除文档
     * @param index
     * @param id
     */
    boolean deleteDocument(String index, String id) throws Exception;

    /**
     * 批量插入
     * @param index
     * @param contents
     */
    boolean bulkRequest(String index, List<User> contents) throws Exception;

    /**
     * 搜索请求
     * @param index
     * @param keyword
     */
    List<Map<String, Object>> searchRequest(String index, String keyword) throws Exception;

    /**
     * 搜索所有id
     * @param index
     */
    List<Integer> searchAllRequest(String index) throws Exception;

}
