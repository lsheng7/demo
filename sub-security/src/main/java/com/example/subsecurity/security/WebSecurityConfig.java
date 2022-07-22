package com.example.subsecurity.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(proxyTargetClass = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public PasswordEncoder passwordEncoder() {
        //spring security 5.0开始必须指定加密方式 否则报错:
        //There is no PasswordEncoder mapped for the id “null“
        return new BCryptPasswordEncoder();
    }


//    public static void main(String[] args) {
//        //$2a$10$/bLurnqJRneb08xatxbi7O6lU6pZ41JGK4DWUNv8B.vIqc7ORgc2e
//        System.out.println(new BCryptPasswordEncoder().encode("root"));
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/user/login")
                .defaultSuccessUrl("/home.html")
//               成功的话可以进行额外处理也可以像上面一样跳转页面
//                .successHandler((request, response, authentication) -> {
//                    log.info("登录成功");
//                    log.info(authentication.getAuthorities().toString());
//                })
                .failureHandler((request, response, exception) -> {
                    log.error(exception.getMessage());
                })
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/user/admin")
//                .hasRole("ADMIN")
                .hasAuthority("ADMIN")
                .and()
                .csrf()
                .disable();
    }
}
