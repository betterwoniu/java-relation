package com.example.basic.service;

import com.example.basic.entity.FileReviewDto;
import com.example.basic.entity.RequestPayload;
import com.example.basic.entity.SensitivityResponseCommon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensitivityService {

   private final WebClient webClient;

   @Value("${sensitivity.basic}")
   private String BASIC;

   @Value("${sensitivity.user}")
   private String USER_NAME;

   @Value("${sensitivity.pwd}")
   private String PASSWORD;
   public String listDetail(List<String> ids){
      try {
         String basicAuth = "Basic " + BASIC;
         ids.add("1970684542810451969");
         Mono<SensitivityResponseCommon<List<FileReviewDto>>> commonMono = webClient.post()
                 .uri(u -> u.path("/api/admin/review-task-api/listDetail").build())
                 .header("Authorization", "bearer ce0ef478-ab60-4551-a0f6-5558c2138912")
                 .bodyValue(new RequestPayload(ids))
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
                 .bodyToMono(new ParameterizedTypeReference<SensitivityResponseCommon<List<FileReviewDto>>>() {
                 });


          System.out.println("获取三方信息:"+commonMono.block());
         return "";
      } catch (Exception ex) {
        log.error("获取三方信息错误",ex);
        return "";
      }
   }
}
