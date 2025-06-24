package com.example.grpcserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.grpcserver.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User getUserByXMLId(@Param("id") String id);
}
