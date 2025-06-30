package org.data.springbootgrpcclient.cotroller;

import lombok.AllArgsConstructor;
import org.data.springbootgrpcclient.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
   public String getUser(){
      return userService.GetUserById("123");
   }
}
