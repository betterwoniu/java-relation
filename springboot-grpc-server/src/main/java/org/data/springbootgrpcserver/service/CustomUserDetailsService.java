package org.data.springbootgrpcserver.service;


import org.data.springbootgrpcserver.entity.CustomUserDetails;
import org.data.springbootgrpcserver.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 自定义登录用户信息认证
 */
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("获取用户信息"+username);
//
//        UserDetails userDetails = User.builder()
//                .username("user")
//                .password("{noop}123")
//                .roles("USER")
//                .build();
//
//
//        return userDetails;


//       UserEntity userEntity = new UserEntity(110,"user", bCryptPasswordEncoder.encode("123"));
//
//        return  new CustomUserDetails(userEntity);
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUsername(username);
        customUserDetails.setPassword(bCryptPasswordEncoder.encode("123"));

        return customUserDetails;
    }

}

