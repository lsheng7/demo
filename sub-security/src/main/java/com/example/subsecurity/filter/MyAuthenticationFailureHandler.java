package com.example.subsecurity.filter;


import cn.hutool.core.util.StrUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        StringBuilder msg = new StringBuilder();
        msg.append(StrUtil.DELIM_START)
                .append("\"error_code\": ")
                .append(401)
                .append(StrUtil.COMMA)
                .append("\"name\": ")
                .append("\"")
                .append(exception.getClass())
                .append("\"")
                .append(StrUtil.COMMA)
                .append("\"message\": ")
                .append("\"")
                .append(exception.getMessage())
                .append("\"")
                .append(StrUtil.DELIM_END);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(msg.toString());
    }
}
