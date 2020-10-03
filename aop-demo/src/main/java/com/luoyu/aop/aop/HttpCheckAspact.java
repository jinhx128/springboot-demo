package com.luoyu.aop.aop;

import com.luoyu.aop.config.KeyConfig;
import com.luoyu.aop.entity.http.HttpRequest;
import com.luoyu.aop.entity.http.HttpResponse;
import com.luoyu.aop.entity.response.TestResponse;
import com.luoyu.aop.util.AESUtil;
import com.luoyu.aop.util.JsonUtils;
import com.oracle.tools.packager.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @Auther: luoyu
 * @Date: 2019/1/22 16:28
 * @Description:
 */
@Aspect
@Component
public class HttpCheckAspact {

    @Autowired
    private KeyConfig keyConfig;

    @Pointcut("@annotation(com.luoyu.aop.aop.HttpCheck)")
    public void pointcut() {
    }

    /**
     *  前置处理
     * @param joinPoint
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        HttpCheck annotation = method.getAnnotation(HttpCheck.class);
        // 获取HttpRequest对象
        Object[] args = joinPoint.getArgs();
        HttpRequest httpRequest = null;
        if(args.length > 0){
            for(Object arg: args){
                if(arg instanceof HttpRequest){
                    httpRequest = (HttpRequest)arg;
                }
            }
        }else {
            throw new Exception("请求参数错误！");
        }

        // 是否需要检测超时时间
        if (annotation.isTimeout()){
            // 获取超时时间
            String timeout = StringUtils.isEmpty(annotation.timeout()) ? keyConfig.getTimeout(): annotation.timeout();
            if(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() < httpRequest.getTime()){
                throw new Exception("请求时间错误！");
            }
            if(LocalDateTime.now().minusSeconds(Integer.parseInt(timeout)).toInstant(ZoneOffset.of("+8")).toEpochMilli() > httpRequest.getTime()){
                throw new Exception("请求已超时！");
            }
            Log.info("检测超时时间成功！");
        }

        // 是否需要进行解密
        if(annotation.isDecrypt()){
            // 获取需要解密的key
            String dectyptKey = StringUtils.isEmpty(annotation.decryptKey()) ? keyConfig.getKeyAesRequest(): annotation.decryptKey();

            String sdt = httpRequest.getSdt();
            if(StringUtils.isEmpty(sdt)){
                throw new Exception("sdt不能为空！");
            }
            String context = AESUtil.decrypt(sdt, dectyptKey);
            if(StringUtils.isEmpty(context)){
                throw new Exception("sdt解密出错！");
            }
            Log.info("解密成功！");
            // 设置解密后的data
            httpRequest.setData(JsonUtils.jsonToObject(context, annotation.dataType()));
        }

    }

    @AfterReturning(value = "pointcut()", returning = "response")
    public void doAfterReturning(JoinPoint joinPoint, Object response) throws Exception {
        HttpResponse httpResponse = (HttpResponse) response;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        HttpCheck annotation = method.getAnnotation(HttpCheck.class);
        if(annotation.isEncrypt()){
            TestResponse body =  (TestResponse) httpResponse.getData();
            // 进行响应加密
            if (body != null) {
                String encrypyKey = StringUtils.isEmpty(annotation.encryptKey())? keyConfig.getKeyAesResponse() : annotation.encryptKey();
                // 设置加密后的srs
                httpResponse.setSrs(AESUtil.encrypt(JsonUtils.objectToJson(body), encrypyKey));
                Log.info("加密成功！");
                httpResponse.setData(null);
            }
        }
    }

}
