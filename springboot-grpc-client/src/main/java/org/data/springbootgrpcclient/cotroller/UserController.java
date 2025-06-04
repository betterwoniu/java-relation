package org.data.springbootgrpcclient.cotroller;

import lombok.AllArgsConstructor;
import org.data.springbootgrpcclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/user")
   public String getUser(){
      return userService.GetUserById("123");
   }
}
