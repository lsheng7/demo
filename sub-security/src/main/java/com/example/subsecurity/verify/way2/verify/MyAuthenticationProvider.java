package com.example.subsecurity.verify.way2.verify;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/***
 * 使用自定义的AuthenticationProvider实现图形验证码的逻辑
 */
@Component
public class MyAuthenticationProvider extends DaoAuthenticationProvider {


    /***
     * 构造函数注入UserDetailsService及PasswordEncoder的Bean对象
     * @param userDetailsService
     * @param passwordEncoder
     */
    public MyAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.setUserDetailsService(userDetailsService);
        this.setPasswordEncoder(passwordEncoder);
    }


    /***
     * 实现验证码的校验
     * @param userDetails 是数据库或者第三方的用户信息
     * @param authentication 是用户表单提交的用户信息
     * @throws AuthenticationException
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        //TODO 实现图形验证码的校验逻辑

        //调用父类方法完成密码验证
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
