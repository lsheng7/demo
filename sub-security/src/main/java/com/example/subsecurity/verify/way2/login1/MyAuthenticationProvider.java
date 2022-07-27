//package com.example.subsecurity.verify.way2.login1;
//
//import javax.annotation.Resource;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
///***
// * 使用自定义的AuthenticationProvider完成用户名及密码的校验
// * 默认情况下是使用内置的DaoAuthenticationProvider
// */
//@Component
//public class MyAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
//
//    @Resource
//    private UserDetailsService userDetailsService;
//
//    @Resource
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
//            throws AuthenticationException {
//        //编写更多的校验逻辑
//
//        //校验密码
//        if (authentication.getCredentials() == null) {
//            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.BadCredentials", "密码不能为空"));
//        } else {
//            final String rawPassword = authentication.getCredentials().toString();
//            final String encodedPassword = userDetails.getPassword();
//            if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
//                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.BadCredentials", "密码错误"));
//            }
//        }
//    }
//
//    @Override
//    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        return userDetailsService.loadUserByUsername(username);
//    }
//}
