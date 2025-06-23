package com.example.grpcserver.service;

import com.example.grpcserver.entity.User;

public interface UserService {

    User getUserByName(String name);

    User getUserByXMLId(String id);
}
