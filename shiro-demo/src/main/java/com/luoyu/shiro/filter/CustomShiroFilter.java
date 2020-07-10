package com.luoyu.shiro.filter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.jinhaoxun.dubbo.constant.AbstractConstant;
import com.jinhaoxun.dubbo.constant.ResponseMsg;
import com.jinhaoxun.dubbo.exception.CustomException;
import com.jinhaoxun.dubbo.model.http.HttpResponse;
import com.jinhaoxun.dubbo.module.user.model.LoginUser;
import com.jinhaoxun.dubbo.module.user.model.UserContext;
import com.jinhaoxun.dubbo.module.user.service.SyncCacheService;
import com.jinhaoxun.dubbo.module.user.service.UserService;
import com.jinhaoxun.dubbo.org.shiro.jwt.Jwt;
import com.jinhaoxun.dubbo.util.requestutil.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @author jinhaoxun
 * @date 2018-05-09
 * @description Shiro的JWT过滤器，代码的执行流程：preHandle->isAccessAllowed->isLoginAttempt->executeLogin->refreshTokenIfNeed
 */
@Slf4j
public class CustomShiroFilter extends BasicHttpAuthenticationFilter implements HandlerInterceptor {

    @Reference
    private SyncCacheService syncCacheService;
    @Reference
    private UserService userService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @author jinhaoxun
     * @description 如果请求头不存在 token，返回用户未登录提示
     * @param request 请求体
     * @param response 响应体
     * @param mappedValue
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //判断请求的请求头是否带上 token
        if (this.isLoginAttempt(request, response)) {
            try {
                //如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确
                this.executeLogin(request, response);
                return true;
            } catch (Exception e) {
                String log = "";
                // 认证出现异常，传递错误信息 msg
                String msg = ResponseMsg.IDENTITY_INFORMATION_IS_EXPIRED.getMsg();
                int code = ResponseMsg.IDENTITY_INFORMATION_IS_EXPIRED.getCode();
                // 获取应用异常(该 Cause 是导致抛出此 throwable (异常)的 throwable (异常))
                Throwable throwable = e.getCause();
                if (throwable != null && throwable instanceof SignatureVerificationException) {
                    // 该异常为 Jwt 的 token 认证失败( token 或者密钥不正确)
                    log = String.format("Token 或者密钥不正确(%s)",throwable.getMessage());
                }else if (throwable != null && throwable instanceof TokenExpiredException) {
                    // 该异常为 Jwt 的 token 已过期
                    log =  String.format("Token 已过期(%s)",throwable.getMessage());
                }else if (throwable != null && throwable instanceof UnauthorizedException) {
                    // 该异常为无权限访问
                    log =  String.format("您没有该权限(%s)",throwable.getMessage());
                    msg =  ResponseMsg.MNG_PERMISSION_DENY.getMsg();
                    code = ResponseMsg.MNG_PERMISSION_DENY.getCode();
                }else if (e instanceof CustomException) {
                    // token已经刷新
                    log = ((CustomException) e).getLog();
                }else {
                    // 应用异常不为空
                    if (throwable != null) {
                        // 获取应用异常 msg
                        log = throwable.getMessage();
                        msg = ResponseMsg.EXCEPTION.getMsg();
                        code = ResponseMsg.EXCEPTION.getCode();
                    }
                }
                /*
                 * 错误两种处理方式
                 * 1.将非法请求转发到/401的Controller处理，抛出自定义无权访问异常被全局捕捉再返回Response信息
                 * 2.无需转发，直接返回Response信息 一般使用第二种(更方便)
                 */
                this.response401(response, log, msg, code);
                return false;
            }
        }else {
            //如果请求头不存在 token，返回用户未登录提示
            this.response401(response, ResponseMsg.USER_NOT_LOG_IN.getMsg(), ResponseMsg.USER_NOT_LOG_IN.getMsg(),ResponseMsg.USER_NOT_LOG_IN.getCode());
            return false;
        }
    }

    /**
     * @author jinhaoxun
     * @description 这里我们详细说明下为什么重写，可以对比父类方法，只是将 executeLogin 方法调用去除了
     * 如果没有去除将会循环调用 doGetAuthenticationInfo 方法
     * @param request 请求体
     * @param response 响应体
     * @return boolean
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response){
        this.sendChallenge(request, response);
        return false;
    }

    /**
     * @author jinhaoxun
     * @description 判断用户是否想要登入，检测 header 里面是否包含 token 字段
     * @param request 请求体
     * @param response 响应体
     * @return boolean
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader(AbstractConstant.REQUEST_AUTH_HEADER);
        return token != null;
    }

    /**
     * @author jinhaoxun
     * @description 执行登陆操作
     * @param request 请求体
     * @param response 响应体
     * @return boolean
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(AbstractConstant.REQUEST_AUTH_HEADER);
        Jwt jwt = new Jwt(token);
        //提交给 customRealm 进行登入，如果错误他会抛出异常并被捕获，如果没有抛出异常则代表登入成功，返回 true
        this.getSubject(request, response).login(jwt);
        String userId = JwtUtil.getClaim(token, AbstractConstant.TOKEN_USER_ID);
        String name = userService.selectName(Long.valueOf(userId));
        // 绑定上下文
        UserContext userContext= new UserContext(new LoginUser(Long.valueOf(userId), name));
        // 检查是否需要更换 Token，需要则重新颁发
        this.refreshTokenIfNeed(userId,token,response);
        return true;
    }

    /**
     * @author jinhaoxun
     * @description 检查是否需要,刷新 Token
     * @param userId 用户id
     * @param authorization token
     * @param response 响应体
     * @return boolean
     * @throws Exception
     */
    private Boolean refreshTokenIfNeed(String userId, String authorization, ServletResponse response) throws Exception {
        Long currentTimeMillis= System.currentTimeMillis();
        if(this.refreshCheck(authorization,currentTimeMillis)){
            //检查刷新规则
            String lockName = AbstractConstant.REFRESH_TOKEN_CHECK_KEY + userId;
            boolean b = syncCacheService.getLock(lockName, AbstractConstant.REFRESH_TOKEN_CHECK_EXPIRATION_TIME);
            //获取到锁
            if (b) {
                String refreshToken= AbstractConstant.REFRESH_TOKEN + userId;
                if(redisTemplate.hasKey(refreshToken)){
                    String tokenTimeStamp = redisTemplate.opsForValue().get(refreshToken).toString();
                    //检查redis中的时间戳与token的时间戳是否一致
                    String tokenMillis= JwtUtil.getClaim(authorization, AbstractConstant.TOKEN_CURRENT_TIME_MILLIS);
                    if(!tokenTimeStamp.equals(tokenMillis)){
                        syncCacheService.releaseLock(lockName,5);
                        String log = String.format("账户：%s 的令牌无效", String.valueOf(userId));
                        throw new CustomException(ResponseMsg.IDENTITY_INFORMATION_IS_EXPIRED.getCode(), log, ResponseMsg.IDENTITY_INFORMATION_IS_EXPIRED.getMsg());
                    }
                }
                log.info(String.format("为账号：%s 颁发新的令牌", String.valueOf(userId)));
                //时间戳一致，则颁发新的令牌
                String password = userService.selectPassword(Long.valueOf(userId));
                String newToken = JwtUtil.createToken(userId, password, String.valueOf(currentTimeMillis));

                redisTemplate.opsForValue().getAndSet(refreshToken, String.valueOf(currentTimeMillis));
                redisTemplate.expire(refreshToken, AbstractConstant.REFRESH_TOKEN_CHECK_EXPIRATION_TIME, TimeUnit.MILLISECONDS);

                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setHeader(AbstractConstant.REQUEST_AUTH_HEADER, newToken);
                httpServletResponse.setHeader("Access-Control-Expose-Headers", AbstractConstant.REQUEST_AUTH_HEADER);
            }
            syncCacheService.releaseLock(lockName);
        }
        return true;
    }

    /**
     * @author jinhaoxun
     * @description 检查是否需要更新 Token
     * @param token token
     * @param currentTimeMillis 时间戳
     * @return boolean
     */
    private boolean refreshCheck(String token, Long currentTimeMillis){
        String tokenMillis= JwtUtil.getClaim(token, AbstractConstant.TOKEN_CURRENT_TIME_MILLIS);
        if(tokenMillis!=null){
            return Long.parseLong(tokenMillis) + (AbstractConstant.REFRESH_TOKEN_CHECK_TIME) < currentTimeMillis;
        }
        return false;
    }

    /**
     * @author jinhaoxun
     * @description 对跨域提供支持
     * @param request 请求体
     * @param response 响应体
     * @return boolean
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        //解决 stomp 跨域问题
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        //跨域时会首先发送一个 option 请求，这里我们给 option 请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * @author jinhaoxun
     * @description 无需转发，直接返回 Response 信息
     * @param resp 响应体
     * @param log 打印的日志信息
     * @param msg 返回给前端的信息
     * @param code 响应状态码
     */
    private void response401(ServletResponse resp, String log, String msg, int code) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.setStatus(401);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = httpServletResponse.getWriter();
            CustomShiroFilter.log.info(log);
            JSONObject json = JSONObject.fromObject(HttpResponse.build(code, msg , null));
            String data=json.toString();
            out.append(data);
        } catch (IOException e) {
            CustomShiroFilter.log.error(e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
