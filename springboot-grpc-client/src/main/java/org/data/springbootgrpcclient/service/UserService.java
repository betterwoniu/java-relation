package org.data.springbootgrpcclient.service;


import org.data.springbootgrpcclient.entity.User;

public interface UserService {

    String GetUserById(String id);

    User getUserByName(String name);
}
