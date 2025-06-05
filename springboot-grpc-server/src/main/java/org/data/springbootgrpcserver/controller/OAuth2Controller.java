package org.data.springbootgrpcserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Controller
public class OAuth2Controller {


    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();


    @GetMapping("/user")
    @ResponseBody
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        // 返回用户信息
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @GetMapping("/login")
    public String login() {
        return "login";  // 返回登录页面模板
    }

    @GetMapping("/userinfo")
    public String userinfo(@AuthenticationPrincipal OAuth2User principal ,HttpServletRequest request){

        System.out.println(principal);
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

    }
