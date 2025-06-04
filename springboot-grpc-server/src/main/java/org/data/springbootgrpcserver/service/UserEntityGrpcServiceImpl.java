package org.data.springbootgrpcserver.service;

import com.data.grpc.server.UserEntityServiceGrpc;
import com.data.grpc.server.UserReply;
import com.data.grpc.server.UserRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;


@GrpcService
public class UserEntityGrpcServiceImpl extends UserEntityServiceGrpc.UserEntityServiceImplBase {


    @Override
    public void getUserInfo(UserRequest request, StreamObserver<UserReply> responseObserver) {
        System.out.println("getUserInfo" + request.toString());
        UserReply reply = UserReply.newBuilder().setId("1")
                                                .setName("vva")
                                                .setCreateTime("2023-03-03")
                                                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
