package org.data.springbootgrpcserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.data.springbootgrpcserver.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Controller
public class OAuth2Controller {


    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;


    @GetMapping("/user")
    @ResponseBody
    public Map<String, Object> user() {

        // 返回用户信息
        return Collections.singletonMap("name","zz");
    }

    @GetMapping("/userinfo")
    public String userinfo(Authentication authentication ,HttpServletRequest request){

        getUserInfo();
        return "user";
    }

    @RequestMapping(value = "/login/oauth2",method = RequestMethod.GET)
    public String oauth2Login(){

        return "oauth2-login";
    }

    @GetMapping("/profile")
    @ResponseBody
    public String profile(@AuthenticationPrincipal OAuth2User user) {
        String name = user.getAttribute("name"); // 从user-info端点获取
        String email = user.getAttribute("email");
        return "Hello, " + name + " (" + email + ")";
    }



    @GetMapping("/logout")
    public String performLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/";
    }


    private String getUserInfo(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println( authentication.getDetails());

        return "getUserInfo";
    }
    }
