package com.example.subsecurity.controller;

import com.example.subsecurity.service.CaptchaService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
//
//    @PostMapping("/login")
//    public void login(@RequestParam String username, @RequestParam String password) {
//        log.info("username={},password={}", username, password);
//    }


    @Resource
    private CaptchaService captchaService;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String printAdmin() {
        return "如果你看见这句话，说明你有ROLE_ADMIN角色";
    }

    @GetMapping("/super")
    @PreAuthorize("hasRole('ROLE_SUPER')")
    public String printUser() {
        return "如果你看见这句话，说明你有ROLE_SUPER角色";
    }

    @GetMapping("/captcha")
    public void captcha() {
        captchaService.captcha();
    }
}
