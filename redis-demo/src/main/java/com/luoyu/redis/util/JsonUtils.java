package com.luoyu.redis.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JsonUtils
 *
 * @author luoyu
 * @date 2018/10/08 19:13
 * @description Json工具类，依赖jackson
 */
@Slf4j
public class JsonUtils {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * object转换成json
     */
    public static <T>String objectToJson(T obj){
        if(obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("object转换成json失败：", e);
            return null;
        }
    }

    /**
     * json转换成object
     */
    public static <T>T jsonToObject(String src, Class<T> clazz){
        if(StringUtils.isEmpty(src) || clazz == null){
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) src : objectMapper.readValue(src, clazz);
        } catch (Exception e) {
            log.error("json转换成object失败：", e);
            return null;
        }
    }

    /**
     * json转换成map
     */
    public static <T>Map<String, Object> jsonToMap(String src) {
        if(StringUtils.isEmpty(src)){
            return null;
        }
        try {
            return objectMapper.readValue(src, Map.class);
        } catch (Exception e) {
            log.error("json转换成map失败：", e);
            return null;
        }
    }

    /**
     * json转换成list
     */
    public static <T>List<T> jsonToList(String jsonArrayStr, Class<T> clazz) {
        try{
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
            return objectMapper.readValue(jsonArrayStr, javaType);
        }catch (Exception e) {
            log.error("json转换成list失败：", e);
            return null;
        }
    }

}
