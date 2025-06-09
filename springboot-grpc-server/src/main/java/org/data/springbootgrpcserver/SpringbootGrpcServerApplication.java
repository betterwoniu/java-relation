package org.data.springbootgrpcserver;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.data.springbootgrpcserver.service.UserEntityGrpcServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.io.IOException;

@SpringBootApplication
@EnableCaching
public class SpringbootGrpcServerApplication {

    public static void main(String[] args) throws IOException, InterruptedException {

        SpringApplication.run(SpringbootGrpcServerApplication.class, args);

    }

}
