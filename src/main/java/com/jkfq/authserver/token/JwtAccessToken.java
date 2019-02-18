package com.jkfq.authserver.token;

import com.alibaba.fastjson.JSON;
import com.jkfq.authserver.constant.Constants;
import com.jkfq.authserver.entity.BaseUser;
import com.jkfq.authserver.entity.BaseUserDetail;
import com.jkfq.authserver.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @Author
 * @create 2019-01-31
 **/
@Slf4j
public class JwtAccessToken extends JwtAccessTokenConverter {

    /**
     * 生成token
     *
     * 重写增强token方法,用于自定义一些token返回的信息
     *
     * @param accessToken
     * @param authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        log.info("----==================自定义enhance================----");
        Object baseUserDetailObject = authentication.getPrincipal();
        BaseUserDetail baseUserDetail = null;
        if (baseUserDetailObject instanceof BaseUserDetail){
            log.info("----==================直接转义================----");
            baseUserDetail = (BaseUserDetail) baseUserDetailObject;
        }else {
            log.info("----==================Json转义================----");
            String string = JSON.toJSON(baseUserDetailObject).toString();
            baseUserDetail = JSON.parseObject(string, BaseUserDetail.class);
        }
        // 设置额外用户信息
        BaseUser baseUser = baseUserDetail.getBaseUser();
        baseUser.setUserPassword(null);
        // 将用户信息添加到token额外信息中
        defaultOAuth2AccessToken.getAdditionalInformation().put(Constants.USER_INFO, baseUser);

        return super.enhance(defaultOAuth2AccessToken, authentication);
    }

    /**
     * 解析token
     * @param value
     * @param map
     * @return
     */
    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map){
        OAuth2AccessToken oauth2AccessToken = super.extractAccessToken(value, map);
        convertData(oauth2AccessToken, oauth2AccessToken.getAdditionalInformation());
        return oauth2AccessToken;
    }

    private void convertData(OAuth2AccessToken accessToken,  Map<String, ?> map) {
        accessToken.getAdditionalInformation().put(Constants.USER_INFO,convertUserData(map.get(Constants.USER_INFO)));

    }

    private BaseUser convertUserData(Object map) {
        String json = JsonUtils.deserializer(map);
        BaseUser user = JsonUtils.serializable(json, BaseUser.class);
        return user;
    }
}
