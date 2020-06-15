package com.cris.springbootidempotent.config;

import com.cris.springbootidempotent.interceptor.IdempotentInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器使其生效
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public IdempotentInterceptor idempotentInterceptor() {
        return new IdempotentInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(idempotentInterceptor()).addPathPatterns("/**");
    }
}
