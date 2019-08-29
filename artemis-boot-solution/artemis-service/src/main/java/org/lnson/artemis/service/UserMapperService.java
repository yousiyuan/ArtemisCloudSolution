package org.lnson.artemis.service;

import org.lnson.artemis.entity.User;
import org.lnson.artemis.service.base.BaseMapperService;
import org.lnson.artemis.service.base.ExampleMapperService;

import java.util.List;

public interface UserMapperService extends BaseMapperService<User>, ExampleMapperService<User> {

    public abstract List<User> queryForListByStatus(Integer status);

}
