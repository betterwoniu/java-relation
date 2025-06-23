package org.data.springbootgrpcclient.service;

import com.data.grpc.server.UserEntityServiceGrpc;
import com.data.grpc.server.UserRequest;
import com.example.grpc.GreeterGrpc;
import com.example.grpc.HelloRequest;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class UserGrpcClinentService {


    @GrpcClient("localgrpcserver")
    private UserEntityServiceGrpc.UserEntityServiceBlockingStub blockingStub;

    @GrpcClient("mygrpcserver")
    private GreeterGrpc.GreeterBlockingStub greeterBlockingStub;

    public String getUserInfo(String userId) {
        UserRequest request = UserRequest.newBuilder().setId(userId).build();
        return blockingStub.getUserInfo(request).getName();
    }


    public String helloTestGrpc() {
        HelloRequest helloRequest = HelloRequest.newBuilder().setName("grpc 测试").build();
        return greeterBlockingStub.sayHello(helloRequest).getMessage();
    }
}
