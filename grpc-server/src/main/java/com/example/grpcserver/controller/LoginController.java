package com.example.grpcserver.controller;

import com.example.grpcserver.entity.User;
import com.example.grpcserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET,value = "/userbyId")
    @ResponseBody
    public User getUserById(@RequestParam("id") String id) {
        return userService.getUserByXMLId(id);
    }
}
