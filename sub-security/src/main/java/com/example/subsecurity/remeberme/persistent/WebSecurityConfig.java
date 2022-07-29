package com.example.subsecurity.remeberme.persistent;

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
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(proxyTargetClass = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PersistentTokenRepository tokenRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/user/admin")
//                .hasRole("ADMIN")
//                .hasAuthority("ADMIN")
                .antMatchers("/user/captcha").permitAll()
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
                .failureHandler(new MyAuthenticationFailureHandler())
                .permitAll()
                .and()
                //rememberMe配置后这个位置必须指定userDetailsService
//        cm9vdDoxNjYwMTk0NDc2MTM3OjFlNTVkMjljNTI5OGFiMzc2ZThmZjNiZTQ0ZmU5NzU5是cookie值
//        final byte[] arr = Base64.decode(
//                "cm9vdDoxNjYwMTk0NDc2MTM3OjFlNTVkMjljNTI5OGFiMzc2ZThmZjNiZTQ0ZmU5NzU5");
                //root:1660194476137:1e55d29c5298ab376e8ff3be44fe9759
                //加密串中包含了用户名
//        System.out.println(new String(arr));

//        hashInfo=md5Hex(username+":"+expirationTime+":"+password+":"+key)
//        username+":"+expirationTime+":"+hashInfo
//        root:1660194476137:1e55d29c5298ab376e8ff3be44fe9759
//        1660194476137<==>2022-8-11 13:7:56
                .rememberMe().userDetailsService(userDetailsService)
                .tokenRepository(tokenRepository)
                //防止每次生成的key 随服务重启后发生变化及多实例部署情况下 会导致自动登录策略失效
                .and()
                .csrf()
                .disable();
    }
}
