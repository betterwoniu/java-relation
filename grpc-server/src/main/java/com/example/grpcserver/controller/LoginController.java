package com.example.grpcserver.controller;

import com.example.grpcserver.config.MinIoConfigProperties;
import com.example.grpcserver.entity.User;
import com.example.grpcserver.service.MinioService;
import com.example.grpcserver.service.UserService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioService minioService;
    @Autowired
    private MinIoConfigProperties minioClientConfig;
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/index")
    public String index() throws Exception {

        return "index";
    }

    @RequestMapping(method = RequestMethod.GET,value = "/userbyId")
    @ResponseBody
    public User getUserById(@RequestParam("id") String id) {
        return userService.getUserByXMLId(id);
    }


    public MultipartFile convertToMultipartFile(File file) throws IOException {
        String fileName = file.getName();
        String contentType = Files.probeContentType(file.toPath());
        byte[] content = Files.readAllBytes(file.toPath());

        return new MockMultipartFile(
                fileName,          // 文件名
                fileName,          // 原始文件名
                contentType,       // 内容类型
                content            // 文件内容字节数组
        );
    }
}
