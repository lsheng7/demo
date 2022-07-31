package com.example.subsecurity.logout;

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

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(proxyTargetClass = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/captcha").permitAll()
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

        http.logout()
                //指定接受注销的路由 默认式/logout路由
                .logoutUrl("/myLogout")
                //注销成功 重定向到该路径下
                .logoutSuccessUrl("/login.html")
                //注销成功的处理方式 不同于logoutSuccessUrl的重定向 logoutSuccessHandler更加灵活
//              logoutSuccessHandler()指定登出成功后的处理，如果指定了这个，那么logoutSuccessUrl就不会生效
//                .logoutSuccessHandler(new MyLogoutSuccessHandler())
                //使该用户的HttpSession失效
                .invalidateHttpSession(true)
                //注销成功 删除指定的cookieNames
                .deleteCookies("remember-me", "JSESSIONID")
                //用于注销的处理句柄 允许自定义一些清理策略
                //事实上 LogoutSuccessHandler也能做到
                .addLogoutHandler(new MyLogoutHandler());
    }
}
