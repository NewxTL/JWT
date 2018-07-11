package com.example.auth.service.impl;

import com.example.auth.entity.User;
import com.example.auth.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.getUserByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("this user does not exist or is cancelled!");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.setAuthList(authorities);
        return user;
    }


}
