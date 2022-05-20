package com.qf.config;

import com.qf.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 使拦截器生效
 * 如果spring-boot-starter-web对springmvc的自动配置不够用
 * 那么我们可以自己去扩展springmvc的自动配置
 */
@Component
public class SpringBootConfig implements WebMvcConfigurer {
    @Autowired
    LoginInterceptor loginInterceptor;
    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(loginInterceptor);
        //定义拦截器需要拦截的资源
        registration.addPathPatterns("/frontend/order/**");
    }
}
