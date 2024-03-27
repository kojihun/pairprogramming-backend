package com.develop.pairprogramming.config;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.Enumeration;

@Component
public class AuthorizationExtractor {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public String extractAccessToken(HttpServletRequest request, String tokenType) {
        Enumeration<String> headerValues = request.getHeaders(AUTHORIZATION_HEADER);
        while (headerValues.hasMoreElements()) {
            String headerValue = headerValues.nextElement();
            if (headerValue.toLowerCase().startsWith(tokenType.toLowerCase())) {
                return headerValue.substring(tokenType.length()).trim();
            }
        }
        return Strings.EMPTY;
    }
}