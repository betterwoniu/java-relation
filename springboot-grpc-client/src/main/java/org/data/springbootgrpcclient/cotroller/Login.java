package org.data.springbootgrpcclient.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Login {

    @GetMapping("/login")
    public String Login() {

        return "login";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login/logout")
    @ResponseBody
    public String logout() {
        return "退出登陆";
    }
}
