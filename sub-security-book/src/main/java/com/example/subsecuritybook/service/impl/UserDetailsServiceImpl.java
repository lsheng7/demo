package com.example.subsecuritybook.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.subsecuritybook.bean.UserPo;
import com.example.subsecuritybook.mapper.UserMapper;
import javax.annotation.Resource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPo userPo = userMapper.selectOne(Wrappers.<UserPo>lambdaQuery().eq(UserPo::getName, username));
        String roles = userPo.getRoles();
        userPo.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(roles));
        return userPo;
    }

}
