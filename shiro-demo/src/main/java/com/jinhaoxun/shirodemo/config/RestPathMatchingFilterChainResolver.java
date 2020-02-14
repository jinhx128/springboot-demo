package com.jinhaoxun.shirodemo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Description: Shiro配置
 * @Author: jinhaoxun
 * @Date: 2020/1/15 11:20
 * @Version: 1.0.0
 */
@Slf4j
public class RestPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {

    /**
     * @author jinhaoxun
     * @description 构造器
     * @return RestPathMatchingFilterChainResolver
     */
    public RestPathMatchingFilterChainResolver() {
        super();
    }

    /**
     * @author jinhaoxun
     * @description 构造器
     * @param filterConfig 配置参数
     * @return RestPathMatchingFilterChainResolver
     */
    public RestPathMatchingFilterChainResolver(FilterConfig filterConfig) {
        super(filterConfig);
    }

    /**
     * @author jinhaoxun
     * @description 重写用于支持支持restful风格接口
     * @param request 请求体
     * @param response 响应体
     * @param originalChain
     * @return FilterChain
     */
    @Override
    public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
        FilterChainManager filterChainManager = getFilterChainManager();
        if (!filterChainManager.hasChains()) {
            return null;
        }
        String requestUrl = getPathWithinApplication(request);
        String[] urls = null;
        for (String pathPattern : filterChainManager.getChainNames()) {
            urls = pathPattern.split("--");
            if (urls.length == 2) {
                /**
                 * 分割出url+httpMethod,判断httpMethod和request请求的method是否一致,不一致直接false
                 */
                if (WebUtils.toHttp(request).getMethod().toUpperCase().equals(urls[1].toUpperCase())) {
                    pathPattern = urls[0];
                }
            }
            if (pathMatches(pathPattern, requestUrl)) {
                if (log.isTraceEnabled()) {
                    log.trace("Matched path pattern [" + pathPattern + "] for requestURI [" + requestUrl + "].  " +
                            "Utilizing corresponding filter chain...");
                }
                if (urls.length == 2) {
                    pathPattern = pathPattern.concat("--").concat(WebUtils.toHttp(request).getMethod().toUpperCase());
                }
                return filterChainManager.proxy(originalChain, pathPattern);
            }
        }
        return null;
    }
}
