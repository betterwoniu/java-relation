package org.data.springbootgrpcserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Controller
public class OAuth2Controller {
    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @GetMapping("/login/userAndPass")
    public String userAndPassword() {
        return "userAndPass";
    }

    @GetMapping("/user")
    @ResponseBody
    public Map<String, Object> user() {
        // 返回用户信息
        return Collections.singletonMap("name", "zz");
    }

    @GetMapping("/userinfo")
    public String userinfo(Authentication authentication, HttpServletRequest request) {
        return "user";
    }

    @RequestMapping(value = "/login/oauth2", method = RequestMethod.GET)
    public String oauth2Login() {

        return "oauth2-login";
    }


    @GetMapping("/logout")
    public String performLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/";
    }


    @GetMapping("/authenticationuser")
    @ResponseBody
    public Object authenticationUser(@CurrentSecurityContext SecurityContext context) {

       Object o = context.getAuthentication().getPrincipal();


        return context.getAuthentication().getPrincipal();
    }

    @GetMapping("/user-details")
    @ResponseBody
    public String getUserDetails(@CurrentSecurityContext(expression = "authentication.principal")
                                      UserDetails userDetails) {
        return userDetails.toString();
    }
}
