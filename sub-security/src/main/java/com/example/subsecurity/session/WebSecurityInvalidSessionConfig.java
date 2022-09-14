package com.example.subsecurity.session;

import com.example.subsecurity.remeberme.MyAuthenticationFailureHandler;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring-Security 默认会话时效性
 *
 * @author lvsheng
 * @version 1.0.0
 * @date 2022/07/31 13:44
 * @see WebSecurityConfigurerAdapter
 */
@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(proxyTargetClass = true, prePostEnabled = true)
public class WebSecurityInvalidSessionConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //权限及登录配置
        http.authorizeRequests()
                .antMatchers("/test/**").permitAll()
                .antMatchers("/user/captcha").permitAll()
                .antMatchers("/login.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/user/login")
                .defaultSuccessUrl("/home.html")
                .failureHandler(new MyAuthenticationFailureHandler())
                .permitAll()
                .and()
                .rememberMe().userDetailsService(userDetailsService)
                .key("oceanus#123")
                .and()
                .csrf()
                .disable();

        http.sessionManagement()
                //配置Session失效策略
                .invalidSessionStrategy(new MyInvalidSessionStrategy());
//                .invalidSessionUrl("/login.html");

        //注销登录
        http.logout()
                //指定接受注销的路由 默认式/logout路由
                .logoutUrl("/myLogout")
                //注销成功 重定向到该路径下
                .logoutSuccessUrl("/login.html")
                //使该用户的HttpSession失效
                .invalidateHttpSession(true)
                //注销成功 删除指定的cookieNames
                .deleteCookies("remember-me", "JSESSIONID");
    }
}
