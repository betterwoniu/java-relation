package org.data.springbootgrpcclient.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.data.grpc.server.UserEntityServiceGrpc;
import com.data.grpc.server.UserReply;
import com.data.grpc.server.UserRequest;
import io.grpc.Channel;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelFactory;
import org.data.springbootgrpcclient.entity.User;
import org.data.springbootgrpcclient.mapper.UserMapper;
import org.data.springbootgrpcclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private GrpcChannelFactory channelFactory;

    @Autowired
    private UserMapper userMapper;

    @Override
    public String GetUserById(String id) {
        Channel channel = channelFactory.createChannel("localgrpcserver");
//        ManagedChannel  channel = ManagedChannelBuilder.forAddress("0.0.0.0", 9090).usePlaintext().build();
        UserEntityServiceGrpc.UserEntityServiceBlockingStub stub = UserEntityServiceGrpc.newBlockingStub(channel);
        UserRequest request = UserRequest.newBuilder().setId(id).build();
        UserReply userReply = stub.getUserInfo(request);

        return userReply.toString();
    }

    @Override
    public User getUserByName(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return userMapper.selectOne(queryWrapper);
    }
}
