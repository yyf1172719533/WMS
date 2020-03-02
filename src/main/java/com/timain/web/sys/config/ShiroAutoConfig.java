package com.timain.web.sys.config;

import com.timain.web.sys.realm.UserRealm;
import lombok.Data;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/14 21:13
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(value = { SecurityManager.class })
@ConfigurationProperties(prefix = "shiro")
@Data
public class ShiroAutoConfig {

    private static final String SHIRO_DIALECT = "shiroDialect";
    private static final String SHIRO_FILTER = "shiroFilter";
    private String hashAlgorithmName = "md5";// 加密方式
    private int hashIterations = 2;// 散列次数
    private String loginUrl = "/index.html";// 默认的登陆页面

    private String[] anonUrls;
    private String logOutUrl;
    private String[] authcUlrs;

    /**
     * 凭证匹配器
     * @return
     */
    @Bean
    public HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(hashAlgorithmName);//加密方式
        credentialsMatcher.setHashIterations(hashIterations);//加密次数
        return credentialsMatcher;
    }

    /**
     * 声明UserRealm
     * @return
     */
    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher());
        return userRealm;
    }

    /**
     * 配置SecurityManager
     */
    @Bean
    public SecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 注入userRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 配置shiro的过滤器
     */
    @Bean(SHIRO_FILTER)
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        factoryBean.setSecurityManager(securityManager);
        // 设置未登录时要跳转的页面
        factoryBean.setLoginUrl(loginUrl);
        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        // 设置放行的路径
        if (null != anonUrls && anonUrls.length > 0) {
            for (String anon : anonUrls) {
                filterChainDefinitionMap.put(anon, "anon");
            }
        }
        // 设置登出的路径
        if (null != logOutUrl) {
            filterChainDefinitionMap.put(logOutUrl, "logout");
        }
        // 设置拦截的路径
        if (null != authcUlrs && authcUlrs.length > 0) {
            for (String authc : authcUlrs) {
                filterChainDefinitionMap.put(authc, "authc");
            }
        }
        Map<String, Filter> filters=new HashMap<>();
        //配置过滤器
        factoryBean.setFilters(filters);
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }

    /**
     * 注册shiro的委托过滤器，相当于之前在web.xml里面配置的
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> delegatingFilterProxy() {
        FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean = new FilterRegistrationBean<DelegatingFilterProxy>();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName(SHIRO_FILTER);
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    /* 加入注解的使用，不加入这个注解不生效--开始 */
    /**
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    /* 加入注解的使用，不加入这个注解不生效--结束 */

    /**
     * 这里是为了能在html页面引用shiro标签，上面两个函数必须添加，不然会报错
     *
     * @return
     */
    @Bean(name = SHIRO_DIALECT)
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
