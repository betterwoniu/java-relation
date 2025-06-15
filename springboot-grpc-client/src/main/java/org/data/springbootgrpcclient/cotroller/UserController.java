package org.data.springbootgrpcclient.cotroller;

import lombok.AllArgsConstructor;
import org.data.springbootgrpcclient.entity.User;
import org.data.springbootgrpcclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/info")
   public String getUser(){
      return userService.GetUserById("123");
   }

   @GetMapping("/string")
   public String getString(){
        return "Hello World";
   }

   @GetMapping("/name")
   public User getUserByName(@RequestParam String name){
        return userService.getUserByName(name);
   }
}
