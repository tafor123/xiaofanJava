package com.example.cloudshareafter.commen;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 功能：配置拦截器
 * 作者：程序员子凡
 * 日期：2023/9/22 10:19
 */
@Configuration //配置类
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(jwtInterceptor()) // 配置jwt的拦截规则
                .addPathPatterns("/**").excludePathPatterns("/login");
        super.addInterceptors(registry);
    }

    @Bean //注入spring容器
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}
