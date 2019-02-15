package com.jkfq.authserver.dao;

import com.jkfq.authserver.entity.BaseResource;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @Author
 * @create 2019-01-31
 **/
public interface BaseResourceDao {

    List<BaseResource> selectResourcesByUserId(Long userId);
}
