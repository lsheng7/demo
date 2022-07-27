//package com.example.subsecurity.verify.way2.login2;
//
//import cn.hutool.core.util.StrUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Slf4j
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(proxyTargetClass = true, prePostEnabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/user/admin")
////                .hasRole("ADMIN")
//                .hasAuthority("ADMIN")
//                .antMatchers("/user/captcha").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login.html")
//                .loginProcessingUrl("/user/login")
//                .defaultSuccessUrl("/home.html")
////               成功的话可以进行额外处理也可以像上面一样跳转页面
////                .successHandler((request, response, authentication) -> {
////                    log.info("登录成功");
////                    log.info(authentication.getAuthorities().toString());
////                })
//                .failureHandler((request, response, exception) -> {
//                    StringBuilder msg = new StringBuilder();
//                    msg.append(StrUtil.DELIM_START)
//                            .append("\"error_code\": ")
//                            .append(401)
//                            .append(StrUtil.COMMA)
//                            .append("\"name\": ")
//                            .append("\"")
//                            .append(exception.getClass())
//                            .append("\"")
//                            .append(StrUtil.COMMA)
//                            .append("\"message\": ")
//                            .append("\"")
//                            .append(exception.getMessage())
//                            .append("\"")
//                            .append(StrUtil.DELIM_END);
//                    response.setContentType("application/json;charset=utf-8");
//                    response.getWriter().write(msg.toString());
//                })
//                .permitAll()
//                .and()
//                .csrf()
//                .disable();
//    }
//}
