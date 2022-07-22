package com.example.subsecurity.service.impl;

import com.example.subsecurity.bean.SystemUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SystemUserDetailServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SystemUser()
                .setUsername("root")
                .setPassword("$2a$10$/bLurnqJRneb08xatxbi7O6lU6pZ41JGK4DWUNv8B.vIqc7ORgc2e")
                .setRoles("ROLE_ADMIN,ROLE_NORMAL");
    }
}
