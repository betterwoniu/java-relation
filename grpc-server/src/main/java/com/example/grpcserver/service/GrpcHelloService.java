package com.example.grpcserver.service;

import com.example.grpc.GreeterGrpc;
import com.example.grpc.HelloReply;
import com.example.grpc.HelloRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
@GrpcService
public class GrpcHelloService extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        String message = "Hello" + request.getName();

        HelloReply helloReply = HelloReply.newBuilder().setMessage(message).build();

        responseObserver.onNext(helloReply);
        responseObserver.onCompleted();

    }
}
