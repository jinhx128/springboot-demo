package com.luoyu.redis.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * MapUtils
 *
 * @author luoyu
 * @date 2018/10/22 19:38
 * @description Map工具类
 */
@Slf4j
public class MapUtils extends HashMap<String, Object> {

    @Override
    public MapUtils put(String key, Object value) {
        super.put(key,value);
        return this;
    }

    /**
     * object转换成map
     */
    public static <T>Map<String, Object> objectToMap(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            Map<String, Object> map = new HashMap<>();
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
            return map;
        } catch (Exception e) {
            log.error("object转换成map失败：", e);
            return null;
        }
    }

    /**
     * map转换成object
     */
    public static <T>T mapToObject(Map<String, Object> map, Class<T> clazz) {
        if (map == null) {
            return null;
        }
        try {
            T obj = clazz.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
            return obj;
        } catch (Exception e) {
            log.error("map转换成object失败：", e);
            return null;
        }
    }

}
