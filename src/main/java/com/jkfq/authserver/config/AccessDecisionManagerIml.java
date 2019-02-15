package com.jkfq.authserver.config;

import com.alibaba.fastjson.JSON;
import com.jkfq.authserver.constant.Constants;
import com.jkfq.authserver.entity.BaseResource;
import com.jkfq.authserver.entity.BaseUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @Author
 * @create 2019-02-13
 **/
@Slf4j
@ConfigurationProperties(prefix = "security")
public class AccessDecisionManagerIml implements AccessDecisionManager {

    /*private AntPathMatcher matcher = new AntPathMatcher();*/

    /*private String[] ignoreds;*/

    private String url;

    private String httpMethod;

    @Autowired
    private TokenExtractor tokenExtractor;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private HttpServletRequest request;

    // 用户角色redis
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        // 请求路径
        url = getUrl(o);
        // http 方法
        httpMethod = getMethod(o);
        log.info("{}----访问方式----{}",url,httpMethod);

       /* // 不拦截的请求
        for(String path : ignoreds){
            String temp = path.trim();
            if (matcher.match(temp, url)) {
                return;
            }
        }*/
        BaseUser baseUser = null;

        // 获取token中的用户信息。
        Map<String, Object> maps = getAccessToken().getAdditionalInformation();
        Object baseUserObject = maps.get(Constants.USER_INFO);

        if (baseUserObject instanceof BaseUser){
            baseUser = (BaseUser) baseUserObject;
        }else {
            String string = JSON.toJSON(baseUserObject).toString();
            baseUser = JSON.parseObject(string, BaseUser.class);
        }


        String resource_key = Constants.REDIS_USER_RESOURCE + baseUser.getId();
        long size = redisTemplate.opsForList().size(resource_key);
        // 从redis中获取用户的权限列表
        List<Object> resourceList = redisTemplate.opsForList().range(resource_key,0,size);

        for (Object object : resourceList) {
            List<BaseResource> objectList = (List<BaseResource>) object;
            for(BaseResource baseResource : objectList) {
                if(matchUrl(url,baseResource.getResource_uri())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("认证用户----403无权限！");
    }

    private boolean matchUrl(String url, String resourceUrl) {

        List urls = Arrays.asList(url.split("/")).stream().filter(e -> !"".equals(e)).collect(Collectors.toList());
        Collections.reverse(urls);

        List paths = Arrays.asList(resourceUrl.split("/")).stream().filter(e -> !"".equals(e)).collect(Collectors.toList());
        Collections.reverse(paths);

        // 如果数量不相等
        if (urls.size() != paths.size()) {
            return false;
        }

        for(int i = 0; i < paths.size(); i++){
            // 如果是 PathVariable 则忽略
            String item = (String) paths.get(i);
            if (item.charAt(0) != '{' && item.charAt(item.length() - 1) != '}') {
                // 如果有不等于的，则代表 URL 不匹配
                if (!item.equals(urls.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    private OAuth2AccessToken getAccessToken() throws AccessDeniedException {
        OAuth2AccessToken token;
        // 抽取token
        Authentication a = tokenExtractor.extract(request);
        try {
            // 调用JwtAccessTokenConverter的extractAccessToken方法解析token
            token = tokenStore.readAccessToken((String) a.getPrincipal());
        } catch(Exception e) {
            throw new AccessDeniedException("AccessToken Not Found.");
        }
        return token;
    }

    /**
     *  获取请求中的url
     */
    private String getUrl(Object o) {
        //获取当前访问url
        String url = ((FilterInvocation)o).getRequestUrl();
        int firstQuestionMarkIndex = url.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
            return url.substring(0, firstQuestionMarkIndex);
        }
        return url;
    }

    private String getMethod(Object o) {
        return ((FilterInvocation)o).getRequest().getMethod();
    }


    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
