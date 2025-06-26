package com.example.grpcserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    Docket docket(){
        // 定义 Cookie 参数
        Parameter cookieParam = new ParameterBuilder()
                .name("Cookie")  // 注意：必须是 "Cookie" 而不是小写
                .description("自动携带的Cookie")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue("your_cookie_name=cookie_value") // 设置默认值（可选）
                .required(false)
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(Collections.singletonList(cookieParam))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.grpcserver.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
