package org.lnson.artemis.service.impl;

import org.lnson.artemis.dao.UserMapper;
import org.lnson.artemis.entity.User;
import org.lnson.artemis.service.UserMapperService;
import org.lnson.artemis.service.base.impl.BaseMapperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserMapperServiceImpl extends BaseMapperServiceImpl<User> implements UserMapperService {

    @Autowired
    private UserMapper userMapper;

    /**********************************************************************************/

    // 演示：执行超时引发服务降级
    @Override
    public List<User> selectForList(User record) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userMapper.select(record);
    }

    // 演示：执行异常引发服务降级
    @Override
    public Integer updateByPrimaryKey(User record) {
        throw new RuntimeException("测试服务降级:异常");
    }

    /**********************************************************************************/

    @Override
    public int deleteByExample(Class<? extends User> clazz, Object... arguments) {
        Integer userId = (Integer) arguments[0];

        Example example = new Example(clazz);
        example.createCriteria()
                .andEqualTo("userId", userId);
        return userMapper.deleteByExample(example);
    }

    @Override
    public List<User> selectByExample(Class<? extends User> clazz, Object... arguments) {
        Integer status = (Integer) arguments[0];
        String userName = (String) arguments[1];

        Example example = new Example(clazz);
        example.createCriteria()
                .andEqualTo("status", status)
                .andLike("userName", userName);
        return userMapper.selectByExample(example);
    }

    @Override
    public int selectCountByExample(Class<? extends User> clazz, Object... arguments) {
        Integer status = (Integer) arguments[0];

        Example example = new Example(clazz);
        example.createCriteria()
                .andEqualTo("status", status);
        return userMapper.selectCountByExample(example);
    }

    @Override
    public int updateByExample(Class<? extends User> clazz, Object... arguments) {
        User record = (User) arguments[0];

        Example example = new Example(clazz);
        example.createCriteria()
                .andEqualTo("userId", record.getUserId());
        return userMapper.updateByExample(record, example);
    }

    @Override
    public int updateByExampleSelective(Class<? extends User> clazz, Object... arguments) {
        User record = (User) arguments[0];

        Example example = new Example(clazz);
        example.createCriteria()
                .andEqualTo("userId", record.getUserId());
        return userMapper.updateByExampleSelective(record, example);
    }

    @Override
    public List<User> queryForListByStatus(Integer status) {
        return userMapper.queryForListByStatus(status);
    }

}
