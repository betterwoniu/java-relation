package com.example.basic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * webClient 配置类
 */
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient( @Value("${sensitivity.url}") String baseurl){
        return WebClient.builder().baseUrl(baseurl).build();
    }
}
