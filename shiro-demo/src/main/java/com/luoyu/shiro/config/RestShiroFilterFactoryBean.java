package com.luoyu.shiro.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.BeanInitializationException;

/**
 * @version 1.0
 * @author jinhaoxun
 * @date 2018-05-09
 * @description Shiro配置
 */
@Slf4j
public class RestShiroFilterFactoryBean extends ShiroFilterFactoryBean {

    /**
     * @author jinhaoxun
     * @description 构造器
     * @return RestShiroFilterFactoryBean
     */
    public RestShiroFilterFactoryBean() {
        super();
    }

    /**
     * @author jinhaoxun
     * @description 重写用于支持支持restful风格接口
     * @return AbstractShiroFilter
     */
    @Override
    protected AbstractShiroFilter createInstance() throws Exception {
        log.debug("开始创建Shiro过滤器实例...");
        SecurityManager securityManager = this.getSecurityManager();
        if (securityManager == null) {
            throw new BeanInitializationException("必须设置SecurityManager属性");
        } else if (!(securityManager instanceof WebSecurityManager)) {
            throw new BeanInitializationException("安全管理器未实现websecuritymanager接口");
        } else {
            FilterChainManager manager = this.createFilterChainManager();
            RestPathMatchingFilterChainResolver chainResolver = new RestPathMatchingFilterChainResolver();
            chainResolver.setFilterChainManager(manager);
            log.debug("创建Shiro过滤器实例成功！");
            return new SpringShiroFilter((WebSecurityManager)securityManager, chainResolver);
        }
    }

    /**
     * @author jinhaoxun
     * @description 构建SpringShiroFilter
     * @return SpringShiroFilter
     */
    private static final class SpringShiroFilter extends AbstractShiroFilter {
        protected SpringShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver) {
            super();
            if (webSecurityManager == null) {
                throw new IllegalArgumentException("WebSecurityManager属性不能为空");
            }
            setSecurityManager(webSecurityManager);
            if (resolver != null) {
                setFilterChainResolver(resolver);
            }
        }
    }
}