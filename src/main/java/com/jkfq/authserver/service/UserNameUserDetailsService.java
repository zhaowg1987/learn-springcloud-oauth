package com.jkfq.authserver.service;

import com.jkfq.authserver.dao.BaseUserDao;
import com.jkfq.authserver.entity.BaseUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 根据用户名登录
 *
 * @Author
 * @create 2019-01-31
 **/
@Slf4j
@Service
public class UserNameUserDetailsService extends BaseUserDetailService {

    @Autowired
    private BaseUserDao baseUserDao;

    /**
     * 获取用户信息
     * @param userName
     * @return
     */
    @Override
    protected BaseUser getUser(String userName) {
        // 从数据库中根据用户名获取用户。
        BaseUser baseUser = baseUserDao.selectBaseUserByUserName(userName);
        if(null == baseUser) {
            log.error("找不到该用户，用户名：{}",userName);
            throw new UsernameNotFoundException("找不到该用户，用户名：" + userName);
        }
        return baseUser;
    }
}
