package com.jkfq.authserver.dao;

import com.jkfq.authserver.entity.BaseRole;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @Author
 * @create 2019-01-31
 **/
public interface BaseRoleDao {

    List<BaseRole> selectRolesByUserId(long userId);


}
