package org.data.springbootgrpcserver.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;

@Controller
public class OAuth2Controller {

    @GetMapping("/")
    public String home() {
        return "index";  // 返回主页模板
    }

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
}
