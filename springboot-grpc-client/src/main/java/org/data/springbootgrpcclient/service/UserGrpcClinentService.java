package org.data.springbootgrpcclient.service;

import com.data.grpc.server.UserEntityServiceGrpc;
import com.data.grpc.server.UserReply;
import com.data.grpc.server.UserRequest;
import com.example.grpc.GreeterGrpc;
import com.example.grpc.HelloRequest;
import com.service.grpc.user.UserServiceGrpc;
import com.service.grpc.user.UserServiceReply;
import com.service.grpc.user.UserServiceRequest;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.data.springbootgrpcclient.entity.User;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserGrpcClinentService {


    @GrpcClient("localgrpcserver")
    private UserEntityServiceGrpc.UserEntityServiceBlockingStub blockingStub;

    @GrpcClient("mygrpcserver")
    private GreeterGrpc.GreeterBlockingStub greeterBlockingStub;

    @GrpcClient("mygrpcserver")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    public String getUserInfo(String userId) {
        UserRequest request = UserRequest.newBuilder().setId(userId).build();
        return blockingStub.getUserInfo(request).getName();
    }


    public String helloTestGrpc() {
        HelloRequest helloRequest = HelloRequest.newBuilder().setName("grpc 测试").build();
        return greeterBlockingStub.sayHello(helloRequest).getMessage();
    }

    public User getGrpcUser(String id){
        UserServiceRequest userServiceRequest = UserServiceRequest.newBuilder().setId(id).build();
        UserServiceReply userReply = userServiceBlockingStub.getUser(userServiceRequest);
        User user = new User();
        user.setId(Integer.valueOf(userReply.getId()));
        user.setName(userReply.getName());
        user.setCreatetime(Timestamp.valueOf(userReply.getCreatetime()));
        user.setUpdatetime(Timestamp.valueOf(userReply.getUpdatetime()));

        return user;
    }
}
