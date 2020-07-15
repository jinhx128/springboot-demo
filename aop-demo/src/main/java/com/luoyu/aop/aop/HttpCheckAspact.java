package com.luoyu.aop.aop;

import com.alibaba.fastjson.JSON;
import com.luoyu.aop.util.EncodeUtil;
import com.luoyu.aop.util.StringUtil;
import com.luoyu.aop.vo.http.HttpRequest;
import com.luoyu.aop.vo.http.HttpResponse;
import com.luoyu.aop.vo.response.TestResponse;
import com.sun.tools.javac.util.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @Auther: jinhaoxun
 * @Date: 2019/1/22 16:28
 * @Description:
 */
@Aspect
@Component
public class HttpCheckAspact {

    /**
     * 加密请求的默认key
     */
    private static String REQUEST_KEY = "w@sd8dlm";
    /**
     * 加密响应的默认key
     */
    private static String RESPONSE_KEY = "#ems&koq";

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
        // 获取request对象
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        // 获取HttpRequest对象
        Object[] args = joinPoint.getArgs();
        HttpRequest httpRequest = null;
        if(args.length > 0){
            for(Object arg: args){
                if(arg instanceof HttpRequest){
                    httpRequest = (HttpRequest)arg;
                }
            }
        }
        // 是否需要进行解密
        boolean isDecrypt = annotation.isDecrypt();
        // 解密的key
        String dectyptKey = annotation.decryptKey();
        Class<?> dataType = annotation.dataType();
        // 获取需要解密的key
        String key = StringUtil.isEmpty(dectyptKey) ? REQUEST_KEY: dectyptKey;

        if(isDecrypt){
            decryption(httpRequest, key, dataType);
        }

    }

    @AfterReturning(value = "pointcut()", returning = "response")
    public void doAfterReturning(JoinPoint joinPoint, Object response) throws Exception {
        HttpResponse httpResponse = (HttpResponse) response;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        HttpCheck annotation = method.getAnnotation(HttpCheck.class);
        String encrypyKey = annotation.encryptKey();
        String key = StringUtil.isEmpty(encrypyKey)? RESPONSE_KEY : encrypyKey;
        boolean isEncrypt = annotation.isEncrypt();
        if(isEncrypt){
            TestResponse body =  (TestResponse) httpResponse.getData();
            if (body != null) {
                  httpResponse.setSrs(EncodeUtil.encryptByAES(body.toString(), key));
                  httpResponse.setData(null);
            }
        }
    }

    /**
     * 解密
     * @param httpRequest
     * @param key
     * @param dataType
     * @throws Exception
     */
    private void decryption(HttpRequest httpRequest, String key, Class<?> dataType) throws  Exception{
        String sdt = httpRequest.getSdt();
        if(StringUtil.isEmpty(sdt)){
            return;
        }
        String context = EncodeUtil.decryptByAES(sdt, key);
        if(StringUtil.isEmpty(context)){
            return;
        }
        httpRequest.setData(JSON.parseObject(context, dataType));
    }
}
