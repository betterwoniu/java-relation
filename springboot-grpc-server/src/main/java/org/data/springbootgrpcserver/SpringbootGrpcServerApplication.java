package org.data.springbootgrpcserver;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.data.springbootgrpcserver.service.UserEntityGrpcServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SpringbootGrpcServerApplication {

    public static void main(String[] args) throws IOException, InterruptedException {

        SpringApplication.run(SpringbootGrpcServerApplication.class, args);


//        ServerBuilder serverBuilder = ServerBuilder.forPort(9090);
//
//        serverBuilder.addService(new UserEntityGrpcServiceImpl());
//
//        Server server = serverBuilder.build();
//        server.start();
//        server.awaitTermination();
    }

}
