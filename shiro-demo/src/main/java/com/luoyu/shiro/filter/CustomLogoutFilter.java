package com.luoyu.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.jinhaoxun.dubbo.constant.ResponseMsg;
import com.jinhaoxun.dubbo.exception.ExceptionFactory;
import com.jinhaoxun.dubbo.model.http.HttpResponse;
import com.jinhaoxun.dubbo.module.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @version 1.0
 * @author jinhaoxun
 * @date 2018-05-09
 * @description 用户退出登录过滤器
 */
@Slf4j
public class CustomLogoutFilter extends LogoutFilter {

    @Resource
    private ExceptionFactory exceptionFactory;

    /**
     * @author jinhaoxun
     * @description 用户退出登录状态
     * @param request 请求体
     * @param response 响应体
     * @return boolean 是否退出成功
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        try {
            subject.logout();
        } catch (Exception e) {
            throw exceptionFactory.build(ResponseMsg.USER_LOG_OUT_FAIL.getCode(),ResponseMsg.USER_LOG_OUT_FAIL.getMsg()+"："+ e.getMessage());
        }
        Long userId = Long.valueOf(request.getParameter("userId"));
        userService.deleteSession(userId);
        this.writeResult(response);
        //不执行后续的过滤器
        return false;
    }

    /**
     * @author jinhaoxun
     * @description 编辑返回的响应结果
     * @param response 响应体
     * @throws Exception
     */
    private void writeResult(ServletResponse response) throws Exception {
        //响应 Json 结果
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(JSON.toJSONString(HttpResponse.buildSuccess()));
        } catch (IOException e) {
            throw exceptionFactory.build(ResponseMsg.USER_LOG_OUT_FAIL.getCode(),"返回Response信息出现IOException异常:" + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
