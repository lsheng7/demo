//package com.example.subsecurity.verify.way2.login;
//
//import javax.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
//
//    @Resource
//    private AuthenticationProvider authenticationProvider;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //使用自定义的provider来完成用户认证的逻辑 默认情况下使用的是DaoAuthenticationProvider
//        auth.authenticationProvider(authenticationProvider);
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
//                .failureHandler((request, response, exception) -> log.error(exception.getMessage()))
//                .permitAll()
//                .and()
//                .csrf()
//                .disable();
//    }
//}
