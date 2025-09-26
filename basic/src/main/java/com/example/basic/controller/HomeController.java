package com.example.basic.controller;

import com.example.basic.entity.*;
import com.example.basic.task.TaskIndex;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Api(value = "", tags = "1、首页")
@RequestMapping("/api/home/")
@RequiredArgsConstructor
public class HomeController {


    private final TaskIndex taskIndex;

    @GetMapping(value = "/home")
    public ResponseResult<String> home(){
        return ResponseResult.success("hello world");
    }
}
