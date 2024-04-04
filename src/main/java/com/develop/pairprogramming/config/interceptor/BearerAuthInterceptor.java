package com.develop.pairprogramming.config.interceptor;

import com.develop.pairprogramming.config.AuthorizationExtractor;
import com.develop.pairprogramming.config.JwtTokenProvider;
import com.develop.pairprogramming.exception.NotValidateTokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class BearerAuthInterceptor implements HandlerInterceptor {
    private final AuthorizationExtractor authorizationExtractor;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = authorizationExtractor.extractAccessToken(request, "Bearer");
        
        // 추출한 AccessToken의 유효성 검사
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new NotValidateTokenException("로그인을 진행해주세요.");
        }

        // 추출한 AccessToken으로부터 Member객체의 id를 얻어옴
        Long memberId = jwtTokenProvider.extractMemberId(accessToken);
        request.setAttribute("memberId", memberId);
        
        return true;
    }
}