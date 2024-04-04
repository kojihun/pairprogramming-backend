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

    /**
     * 요청과 응답을 가로채기 위해 인터셉터를 추가
     * @param registry 인터셉터를 등록하는 InterceptorRegistry 객체
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bearerAuthInterceptor)
                .excludePathPatterns(
                        "/api/members/signin",
                        "/api/members/signup",
                        "/api/members/validation",
                        "/api/problems/all",
                        "/api/problems/compile"
                )
                .addPathPatterns(
                        "/api/members/**",
                        "/api/problems/**"
                );
    }
}