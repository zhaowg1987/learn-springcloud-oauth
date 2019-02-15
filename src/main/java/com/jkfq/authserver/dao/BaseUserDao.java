package com.jkfq.authserver.dao;

import com.jkfq.authserver.entity.BaseUser;

/**
 * ${DESCRIPTION}
 *
 * @Author
 * @create 2019-01-31
 **/
public interface BaseUserDao {

    /**
     * 根据用户名获取用户信息
     * @return
     */
    BaseUser selectBaseUserByUserName(String userName);

}
