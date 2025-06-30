package org.data.springbootgrpcclient.service;

import com.data.grpc.server.UserEntityServiceGrpc;
import com.data.grpc.server.UserRequest;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class UserGrpcClinentService {


    @GrpcClient("userserver")
    private UserEntityServiceGrpc.UserEntityServiceBlockingStub blockingStub;

    public String getUserInfo(String userId) {
        UserRequest request = UserRequest.newBuilder().setId(userId).build();
        return blockingStub.getUserInfo(request).getName();
    }

}
