package org.lnson.artemis.dao;

import org.apache.ibatis.annotations.Param;
import org.lnson.artemis.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {

    public abstract List<User> queryForListByStatus(@Param("status") Integer status);

}
