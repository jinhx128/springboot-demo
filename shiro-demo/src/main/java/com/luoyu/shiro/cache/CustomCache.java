package com.luoyu.shiro.cache;

import com.alibaba.fastjson.JSONObject;
import com.luoyu.shiro.common.AbstractConstant;
import com.luoyu.shiro.jwt.util.JwtUtil;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @author jinhaoxun
 * @date 2018-05-09
 * @description 重写 Shiro 的 Cache 保存读取
 */
public class CustomCache<K,V> implements Cache<K,V> {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @author jinhaoxun
     * @description 缓存的 key 名称获取为 shiro:userId
     * @param key 缓存的 key
     * @return  String 获取 key 的内容
     */
    private String getKey(Object key){
        return AbstractConstant.SHIRO_ROLE_PERMISSION_KEY + JwtUtil.getClaim(key.toString(), AbstractConstant.TOKEN_USER_ID);
    }

    /**
     * @author jinhaoxun
     * @description 获取缓存
     * @param key 缓存的 key
     * @return Object 获取 key 的内容
     * @throws CacheException
     */
    @Override
    public Object get(Object key) throws CacheException {
        if(!redisTemplate.hasKey(this.getKey(key))){
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(redisTemplate.opsForValue().get(this.getKey(key)).toString());
        AuthorizationInfo authorizationInfo = JSONObject.toJavaObject(jsonObject,AuthorizationInfo.class);
        return authorizationInfo;
    }

    /**
     * @author jinhaoxun
     * @description 保存缓存
     * @param key 保存的 key
     * @param value 保存的 value
     * @return Object
     * @throws CacheException
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        // 设置 Redis 的 Shiro 缓存
        redisTemplate.opsForValue().set(this.getKey(key), value, AbstractConstant.SHIRO_ROLE_PERMISSION_EXPIRATION_TIME, TimeUnit.SECONDS);
        return value;
    }

    /**
     * @author jinhaoxun
     * @description 移除缓存
     * @param key 移除的 key
     * @return Object
     * @throws CacheException
     */
    @Override
    public Object remove(Object key) throws CacheException {
        if(!redisTemplate.hasKey(this.getKey(key))){
            return null;
        }
        redisTemplate.delete(this.getKey(key));
        return null;
    }

    /**
     * @author jinhaoxun
     * @description 清空所有缓存
     * @throws CacheException
     */
    @Override
    public void clear() throws CacheException {
        Set<String> keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }

    /**
     * @author jinhaoxun
     * @description 获取缓存的个数
     * @return int 缓存的个数
     */
    @Override
    public int size() {
        Set<String> keys = redisTemplate.keys("*");
        return keys.size();
    }

    /**
     * @author jinhaoxun
     * @description 获取所有的 key
     * @return Set 所有的 key
     */
    @Override
    public Set keys() {
        Set<String> keys = redisTemplate.keys("*");
        Set<Object> set = new HashSet<Object>();
        for (String bs : keys) {
            set.add(bs);
        }
        return set;
    }

    /**
     * @author jinhaoxun
     * @description 获取所有的 value
     * @return Collection 所有的 value
     */
    @Override
    public Collection values() {
        Set keys = this.keys();
        List<Object> values = new ArrayList<Object>();
        for (Object key : keys) {
            JSONObject jsonObject = JSONObject.parseObject(String.valueOf(redisTemplate.opsForSet().members(this.getKey(key))));
            AuthorizationInfo authorizationInfo = JSONObject.toJavaObject(jsonObject,AuthorizationInfo.class);
            values.add(authorizationInfo);
        }
        return values;
    }
}
