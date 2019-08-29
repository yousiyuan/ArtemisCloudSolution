package org.lnson.artemis.service.base.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lnson.artemis.service.base.BaseMapperService;
import org.lnson.artemis.service.base.ExampleMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.BaseMapper;

import java.io.Serializable;
import java.util.List;

public class BaseMapperServiceImpl<T extends Serializable> implements BaseMapperService<T>, ExampleMapperService<T> {
    @Autowired
    private BaseMapper<T> baseMapper;

    @Override
    public List<T> selectForList() {
        return baseMapper.selectAll();
    }

    @Override
    public List<T> selectForList(T record) {
        return baseMapper.select(record);
    }

    @Override
    public PageInfo<T> selectForPageList(Integer pageNum, Integer pageSize, String orderBy, T record) {
        PageHelper.startPage(pageNum, pageSize).setOrderBy(orderBy);
        return new PageInfo<>(this.selectForList(record));
    }

    @Override
    public T selectForObject(Object primaryKey) {
        return baseMapper.selectByPrimaryKey(primaryKey);
    }

    @Override
    public T selectForObject(T record) {
        return baseMapper.selectOne(record);
    }

    @Override
    public Integer selectCount(T record) {
        return baseMapper.selectCount(record);
    }

    @Override
    public Integer insert(T record) {
        return baseMapper.insert(record);
    }

    @Override
    public Integer insertSelective(T record) {
        return baseMapper.insertSelective(record);
    }

    @Override
    public Integer updateByPrimaryKey(T record) {
        return baseMapper.updateByPrimaryKey(record);
    }

    @Override
    public Integer updateByPrimaryKeySelective(T record) {
        return baseMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Integer delete(T record) {
        return baseMapper.delete(record);
    }

    @Override
    public Integer deleteByPrimaryKey(Object primaryKey) {
        return baseMapper.deleteByPrimaryKey(primaryKey);
    }
}
