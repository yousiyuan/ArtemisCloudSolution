package org.lnson.artemis.service.base;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

public interface BaseMapperService<T extends Serializable> {
    public abstract List<T> selectForList();

    public abstract List<T> selectForList(T record);

    public abstract PageInfo<T> selectForPageList(Integer pageNum, Integer pageSize, String orderBy, T record);

    public abstract T selectForObject(Object primaryKey);

    public abstract T selectForObject(T record);

    public abstract Integer selectCount(T record);

    public abstract Integer insert(T record);

    public abstract Integer insertSelective(T record);

    public abstract Integer updateByPrimaryKey(T record);

    public abstract Integer updateByPrimaryKeySelective(T record);

    public abstract Integer delete(T record);

    public abstract Integer deleteByPrimaryKey(Object primaryKey);
}
