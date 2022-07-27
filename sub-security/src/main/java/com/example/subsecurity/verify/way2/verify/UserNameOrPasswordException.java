package com.example.subsecurity.verify.way2.verify;

import org.springframework.security.core.AuthenticationException;

public class UserNameOrPasswordException extends AuthenticationException {

    public UserNameOrPasswordException() {
        super("用户名或密码码校验失败");
    }
}
