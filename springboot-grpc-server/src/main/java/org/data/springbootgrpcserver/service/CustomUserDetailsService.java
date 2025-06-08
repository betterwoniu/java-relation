package org.data.springbootgrpcserver.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("获取用户信息"+username);

        UserDetails userDetails = User.builder()
                .username("user")
                .password("{noop}123")
                .roles("USER")
                .build();


        return userDetails;
    }
}

