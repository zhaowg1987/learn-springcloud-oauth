package com.jkfq.authserver.controller;

import com.alibaba.fastjson.JSON;
import com.jkfq.authserver.entity.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * ${DESCRIPTION}
 *
 * @Author
 * @create 2019-02-15
 **/
@Slf4j
@RestController
public class UserInfoController {

    @Autowired
    private TokenStore tokenStore;

    /**
     * 资源服务器校验用户信息使用
     * @param user
     * @return
     */
    @RequestMapping("oauth/user")
    public Principal user(Principal user) {
        return user;
    }


    /**
     * 退出时删除token
     * @param request
     * @return
     */
    @RequestMapping(value = "/oauth/revoke-token")
    @ResponseBody
    public String logout(HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null) {
                String tokenValue = authHeader.replace("Bearer", "").trim();
                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                tokenStore.removeAccessToken(accessToken);
                // TODO 用户退出时，需要删除redis服务器用户对应的角色、权限资源、refresh相关信息。
                responseData.setCode(200);
                responseData.setMessage("success revoke-token .");
            }
        } catch (Exception e) {
            responseData.setCode(500);
            responseData.setMessage(e.getMessage());
            log.error("revoke-token 异常。",e);
        }
        return JSON.toJSONString(responseData);
    }
}
