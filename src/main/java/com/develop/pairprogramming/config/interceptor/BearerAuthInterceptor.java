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
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new NotValidateTokenException("로그인이 필요한 화면입니다.");
        }

        String memberId = jwtTokenProvider.getSubject(accessToken);
        request.setAttribute("memberId", memberId);

        return true;
    }
}