package org.data.springbootgrpcclient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.data.springbootgrpcclient.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
