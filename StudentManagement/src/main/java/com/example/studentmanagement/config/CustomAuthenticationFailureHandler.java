package com.example.studentmanagement.config;

import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        org.springframework.security.core.AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 设置 HTTP 401 状态码
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"Invalid username or password\"}");
    }
}
