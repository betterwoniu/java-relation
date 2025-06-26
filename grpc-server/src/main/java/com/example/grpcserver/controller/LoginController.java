package com.example.grpcserver.controller;

import com.example.grpcserver.config.MinIoConfigProperties;
import com.example.grpcserver.entity.User;
import com.example.grpcserver.service.MinioService;
import com.example.grpcserver.service.UserService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@Controller
@Api(value = "", tags = "登录")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioService minioService;
    @Autowired
    private MinIoConfigProperties minioClientConfig;


    @ApiOperation(value = "账号密码登录")
    @GetMapping("/login/userAndPass")
    public String login(){
        return "login";
    }

    @ApiOperation(value = "index")
    @GetMapping({"/","/index"})
    public String index()  {
        return "index";
    }

    @ApiOperation(value = "home")
    @GetMapping("/home")
    public String home(){
        return "home";
    }


    @ApiOperation(value = "userbyId")

    @RequestMapping(method = RequestMethod.GET,value = "/userbyId")
    @ResponseBody
    public User getUserById(@RequestParam("id") String id) {
        return userService.getUserByXMLId(id);
    }
}
