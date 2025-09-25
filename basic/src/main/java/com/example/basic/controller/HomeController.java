package com.example.basic.controller;

import com.example.basic.entity.ResponseResult;
import com.example.basic.entity.SensitivityResponseCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class HomeController {

    @Autowired
    private WebClient webClient;

    @Value("${sensitivity.basic}")
    private String BASIC;

    @Value("${sensitivity.user}")
    private String USER_NAME;

    @Value("${sensitivity.pwd}")
    private String PASSWORD;
    @GetMapping(value = "/home")
    public ResponseResult<String> home(){

        String basicAuth = "Basic " + BASIC;
        Mono<SensitivityResponseCommon<String>> commonMono = webClient.post()
                .uri(u -> u.path("/api/auth/oauth/token").build())
                .header("Authorization", basicAuth)
                .body(BodyInserters.fromFormData("grant_type", "password")
                        .with("username", USER_NAME)
                        .with("password", "123"))
                .retrieve()
                // 处理非 2xx 状态码
                .onStatus(HttpStatus::is4xxClientError, response ->
                        response.bodyToMono(String.class).flatMap(body ->
                                Mono.error(new RuntimeException("客户端错误 " + response.statusCode() + "：" + body))
                        )
                )
                .onStatus(HttpStatus::is5xxServerError, response ->
                        response.bodyToMono(String.class).flatMap(body ->
                                Mono.error(new RuntimeException("服务端错误 " + response.statusCode() + "：" + body))
                        )
                )
                .bodyToMono(new ParameterizedTypeReference<SensitivityResponseCommon<String>>() {
                });

        System.out.println(commonMono.block());
        return ResponseResult.success("hello world");
    }
}
