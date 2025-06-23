package com.example.grpcserver.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.grpcserver.mapper.UserMapper;
import com.example.grpcserver.entity.User;
import com.example.grpcserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserByName(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User getUserByXMLId(String id) {
        return userMapper.getUserByXMLId(id);
    }
}
