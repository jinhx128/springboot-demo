package com.jinhaoxun.shiro.config;

import com.jinhaoxun.shiro.cache.CustomCacheManager;
import com.jinhaoxun.shiro.filter.CustomLogoutFilter;
import com.jinhaoxun.shiro.filter.CustomShiroFilter;
import com.jinhaoxun.shiro.realm.CustomRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @version 1.0
 * @author jinhaoxun
 * @date 2018-05-09
 * @description Shiro配置
 */
@Configuration
public class ShiroConfig {

    /**
     * @author jinhaoxun
     * @description 自定义的RestShiroFilterFactoryBean，先走 filter ，然后 filter 如果检测到请求头存在 token，则用 token 去 login，走 Realm 去验证
     * @param securityManager
     * @return RestShiroFilterFactoryBean
     */
    @Bean
    public RestShiroFilterFactoryBean factory(SecurityManager securityManager) {
        RestShiroFilterFactoryBean factoryBean = new RestShiroFilterFactoryBean();

        Map<String, Filter> filterMap = new HashMap<>();
        // 添加自己的过滤器并且取名为jwt
        filterMap.put("customshirofilter", customShiroFilter());
        filterMap.put("logout", new CustomLogoutFilter());
        factoryBean.setFilters(filterMap);

        factoryBean.setSecurityManager(securityManager);
        //factoryBean.setUnauthorizedUrl("/401");// 设置无权限时跳转的 url
        //自定义url规则，http://shiro.apache.org/web.html#urls-
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        //Swagger2
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/webjars/springfox-swagger-ui/**", "anon");
        filterChainDefinitionMap.put("/csrf", "anon");
        filterChainDefinitionMap.put("/", "anon");

        //Shiro、登录注册
        filterChainDefinitionMap.put("/user/code", "anon");
        filterChainDefinitionMap.put("/user/session", "anon");
        filterChainDefinitionMap.put("/user/codesession", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/user/user--POST", "anon");

        filterChainDefinitionMap.put("/article/**--GET", "anon");
        filterChainDefinitionMap.put("/rabbitmq/**", "anon");
        filterChainDefinitionMap.put("/rocketmq/**", "anon");
        filterChainDefinitionMap.put("/test/**", "anon");

        // 其余所有请求通过自定义的Filter
        //filterChainDefinitionMap.put("/**", "customshirofilter");
        // 访问 /unauthorized/** 不通过JWTFilter
        //filterRuleMap.put("/401", "anon");
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }

    /**
     * @author jinhaoxun
     * @description 注入 securityManager
     * @param customRealm 自定义的realm
     * @return SecurityManager
     */
    @Bean
    public SecurityManager securityManager(CustomRealm customRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置自定义 realm
        securityManager.setRealm(customRealm);
        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        // 设置自定义缓存(Cache)管理器
        securityManager.setCacheManager(new CustomCacheManager());
        return securityManager;
    }

    /**
     * @author jinhaoxun
     * @description 扫描上下文，寻找所有的Advistor(通知器），将这些Advisor应用到所有符合切入点的Bean中
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();

        //强制使用cglib，防止重复代理和可能引起代理出错的问题，https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * @author jinhaoxun
     * @description 匹配所有类，匹配所有加了认证注解的方法
     * @param securityManager
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * @author jinhaoxun
     * @description 将Initializable和Destroyable的实现类统一在其内部自动分别调用了Initializable.init()
     * 和Destroyable.destroy()方法，从而达到管理shiro bean生命周期的目的
     * @return LifecycleBeanPostProcessor
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * @author jinhaoxun
     * @description 注入自定义filter
     * @return CustomShiroFilter
     */
    @Bean
    public CustomShiroFilter customShiroFilter(){
        return new CustomShiroFilter();
    }

    /**
     * @author jinhaoxun
     * @description 解决自定义Filter后，出现设置anon等级无效的问题
     * @param customShiroFilter 自定义过滤器
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean registerJwtFilter(@Autowired CustomShiroFilter customShiroFilter) {
        //设置jwt filter不自动注册到spring管理的监听器中，防止与shiro filter同级，导致该监听器必定执行
        FilterRegistrationBean<CustomShiroFilter> jwtFilterRegister = new FilterRegistrationBean<>(customShiroFilter);
        jwtFilterRegister.setEnabled(false);
        return jwtFilterRegister;
    }
}