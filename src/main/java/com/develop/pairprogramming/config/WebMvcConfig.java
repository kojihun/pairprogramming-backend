package com.develop.pairprogramming.config;

import com.develop.pairprogramming.config.interceptor.BearerAuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final BearerAuthInterceptor bearerAuthInterceptor;

    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(bearerAuthInterceptor)
                .excludePathPatterns("/api/members/signin", "/api/members/signup", "/api/members/validation")
                .addPathPatterns("/api/members/**");
    }
}