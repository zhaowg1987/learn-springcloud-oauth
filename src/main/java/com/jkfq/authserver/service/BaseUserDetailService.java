package com.jkfq.authserver.service;

import com.jkfq.authserver.constant.Constants;
import com.jkfq.authserver.dao.BaseResourceDao;
import com.jkfq.authserver.dao.BaseRoleDao;
import com.jkfq.authserver.entity.BaseResource;
import com.jkfq.authserver.entity.BaseRole;
import com.jkfq.authserver.entity.BaseUser;
import com.jkfq.authserver.entity.BaseUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象类实现UserDetailsService接口
 * 可以有多个实现类 以后扩展起来方便
 * @Author
 * @create 2019-01-31
 **/
@Slf4j
public abstract class BaseUserDetailService implements UserDetailsService {

    @Autowired
    private BaseRoleDao baseRoleDao;

    @Autowired
    private BaseResourceDao baseResourceDao;

    // 用户角色redis
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

     /*   BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("admin");
        log.info("admin对应的加密密码：{}",password);*/

        // 获取用户信息
        BaseUser baseUser = getUser(s);
        // 获取用户角色信息
        List<BaseRole> roleList = baseRoleDao.selectRolesByUserId(baseUser.getId());
        if(null == roleList) {
            roleList = new ArrayList<BaseRole>();
        }
        // 获取用户权限列表
        List<GrantedAuthority> authorities = convertToAuthorities(baseUser, roleList);

        // 存储用户权限到redis中
        List<BaseResource> resourceList = baseResourceDao.selectResourcesByUserId(baseUser.getId());
        redisTemplate.delete(Constants.REDIS_USER_RESOURCE + baseUser.getId());
        if(null != resourceList && resourceList.size() >0) {
            redisTemplate.opsForList().rightPushAll(Constants.REDIS_USER_RESOURCE + baseUser.getId(),resourceList);
        }
        // 构造返回对象
        User user =  new User(baseUser.getUserName(),
                baseUser.getUserPassword(), isActive(baseUser.getIs_active()), true, true, true, authorities);

        return new BaseUserDetail(baseUser, user);
    }

    /**
     * 转换对象
     * @param baseUser
     * @param roleList
     * @return
     */
    private List<GrantedAuthority> convertToAuthorities(BaseUser baseUser, List<BaseRole> roleList) {
        // 清除 Redis 中用户的角色
        redisTemplate.delete(Constants.REDIS_USER_ROLE + baseUser.getId());
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        roleList.forEach(e -> {
            // 存储用户、角色信息到GrantedAuthority，并放到GrantedAuthority列表
            GrantedAuthority authority = new SimpleGrantedAuthority(e.getRoleCode());
            authorities.add(authority);
            //存储角色到redis
            redisTemplate.opsForList().rightPush(Constants.REDIS_USER_ROLE + baseUser.getId(), e);
        });
        return authorities;
    }


    private boolean isActive(String active){
        return "1".equals(active)  ? true : false;
    }

    /**
     * 抽象方法
     * @param var1
     * @return
     */
    protected abstract BaseUser getUser(String var1) ;

}
