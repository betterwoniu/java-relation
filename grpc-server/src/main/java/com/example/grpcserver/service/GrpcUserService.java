package com.example.grpcserver.service;

import com.example.grpcserver.entity.User;
import com.service.grpc.user.UserServiceGrpc;
import com.service.grpc.user.UserServiceReply;
import com.service.grpc.user.UserServiceRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class GrpcUserService extends UserServiceGrpc.UserServiceImplBase{

    @Autowired
    private UserService userService;
    @Override
    public void getUser(UserServiceRequest request, StreamObserver<UserServiceReply> responseObserver) {
        String id = request.getId();
        User user = userService.getUserByXMLId(id);

        UserServiceReply userReply = UserServiceReply.newBuilder()
                .setId(String.valueOf(user.getId()))
                .setName(user.getName())
                .setCreatetime(String.valueOf(user.getCreatetime()))
                .setUpdatetime(String.valueOf(user.getUpdatetime()))
                .build();

        responseObserver.onNext(userReply);
        responseObserver.onCompleted();
    }
}
