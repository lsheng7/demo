package com.example.subsecurity.service.impl;

import com.example.subsecurity.bean.SystemUser;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SystemUserDetailServiceImpl implements UserDetailsService {

    @Resource
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final HttpSession session = request.getSession();
        session.setAttribute("SessionFixation", "SessionFixation");
        return new SystemUser()
                .setUsername("root")
                .setPassword("$2a$10$/bLurnqJRneb08xatxbi7O6lU6pZ41JGK4DWUNv8B.vIqc7ORgc2e")
                .setRoles("ROLE_ADMIN,ROLE_NORMAL");
    }
}
