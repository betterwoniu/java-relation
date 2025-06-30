package org.data.springbootgrpcclient.service.Impl;

import com.data.grpc.server.UserEntityServiceGrpc;
import com.data.grpc.server.UserReply;
import com.data.grpc.server.UserRequest;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelFactory;
import org.data.springbootgrpcclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private GrpcChannelFactory channelFactory;

    @Override
    public String GetUserById(String id) {
        Channel channel = channelFactory.createChannel("localgrpcserver");
//        ManagedChannel  channel = ManagedChannelBuilder.forAddress("0.0.0.0", 9090).usePlaintext().build();
        UserEntityServiceGrpc.UserEntityServiceBlockingStub stub = UserEntityServiceGrpc.newBlockingStub(channel);
        UserRequest request = UserRequest.newBuilder().setId(id).build();

        return stub.getUserInfo(request).getName();
    }
}
