package com.example.subsecurity.session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.session.InvalidSessionStrategy;

public class MyInvalidSessionStrategy implements InvalidSessionStrategy {

    private static final String CONTENT_TYPE_JSON = "application/json;charset=utf-8";

    private static final List<String> WHITE_LIST = new ArrayList<>();

    static {
        WHITE_LIST.add("/user/login");
        WHITE_LIST.add("/login.html");
        WHITE_LIST.add("/myLogout");
        WHITE_LIST.add("/user/captcha");
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //前端处理方式
//        if (!WHITE_LIST.contains(request.getRequestURI())) {
//            response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
//            response.setContentType(CONTENT_TYPE_JSON);
//            response.getWriter().write("session失效");
//        } else {
        response.sendRedirect("/login.html");
//        }
    }
}
