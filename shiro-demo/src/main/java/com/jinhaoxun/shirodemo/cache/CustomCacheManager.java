//package com.jinhaoxun.shirodemo.cache;
//
//import org.apache.shiro.cache.Cache;
//import org.apache.shiro.cache.CacheException;
//import org.apache.shiro.cache.CacheManager;
//
///**
// * @version 1.0
// * @author jinhaoxun
// * @date 2018-05-09
// * @description 重写 Shiro 的 Cache 保存读取
// */
//public class CustomCacheManager implements CacheManager {
//
//    /**
//     * @author jinhaoxun
//     * @description 获取自定义CustomCache
//     * @param s
//     * @return Cache
//     * @throws CacheException
//     */
//    @Override
//    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
//        return new CustomCache<K,V>();
//    }
//}
