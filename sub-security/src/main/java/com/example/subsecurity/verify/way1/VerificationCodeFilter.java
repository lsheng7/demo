//package com.example.subsecurity.verify.way1;
//
//import cn.hutool.core.util.StrUtil;
//import java.io.IOException;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//public class VerificationCodeFilter extends OncePerRequestFilter {
//
//    private static final String LOGIN_URI = "/user/login";
//    private static final String CAPTCHA = "captcha";
//    private final AuthenticationFailureHandler authenticationFailureHandler = new MyAuthenticationFailureHandler();
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        final String requestURI = request.getRequestURI();
//        if (StrUtil.equals(requestURI, LOGIN_URI)) {
//            //登录请求
//            try {
//                checkCaptcha(request);
//                filterChain.doFilter(request, response);
//            } catch (VerificationCodeException exception) {
//                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
//                //过滤链不允许向后执行了
//            }
//        } else {
//            //非登录请求
//            filterChain.doFilter(request, response);
//        }
//    }
//
//    private void checkCaptcha(HttpServletRequest request) throws VerificationCodeException {
//        final String inputCaptcha = request.getParameter(CAPTCHA);
//        final HttpSession session = request.getSession();
//        final String sessionCaptcha = (String) session.getAttribute(CAPTCHA);
//        if (StrUtil.isNotBlank(sessionCaptcha)) {
//            session.removeAttribute(CAPTCHA);
//        }
//        if (StrUtil.isBlank(inputCaptcha) || StrUtil.isBlank(sessionCaptcha) || !StrUtil.equals(inputCaptcha, sessionCaptcha)) {
//            throw new VerificationCodeException();
//        }
//    }
//
//}
