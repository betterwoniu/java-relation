package org.data.springbootgrpcserver.service;


import org.data.springbootgrpcserver.entity.CustomOAuthUser;
import org.data.springbootgrpcserver.entity.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

/**
 * 自定义登录用户信息认证
 */
public class CustomUserDetailsService implements UserDetailsService,OAuth2UserService<OAuth2UserRequest, OAuth2User> {

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
        customUserDetails.setId(00001);
        customUserDetails.setUsername(username);
        customUserDetails.setPassword(bCryptPasswordEncoder.encode("123"));
        customUserDetails.setEmail("security@spring.cn");
        customUserDetails.setPhone("17756894263");
        // 构建权限列表
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));


        customUserDetails.setAuthorities(authorities);

        return customUserDetails;
    }

    /**
     *  使用OAuth2.0登录的自定义用户信息处理
     * @param userRequest
     * @return
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        CustomOAuthUser customOAuthUser = new CustomOAuthUser();

        // 获取默认的 OAuth2User
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        customOAuthUser.setAttributes(oAuth2User.getAttributes());
        customOAuthUser.setAuthorities((Set<GrantedAuthority>) oAuth2User.getAuthorities());
        customOAuthUser.setNameAttributeKey(userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName());

        return  customOAuthUser;
    }
}

