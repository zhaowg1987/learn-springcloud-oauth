package com.jkfq.authserver.error;

import com.alibaba.fastjson.JSON;
import com.jkfq.authserver.entity.ResponseData;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ${DESCRIPTION}
 *
 * @Author
 * @create 2019-02-12
 **/
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("text/javascript;charset=utf-8");
        ResponseData responseData = new ResponseData();
        responseData.setCode(400);
        responseData.setMessage("用户认证失败!");
        httpServletResponse.getWriter().print(JSON.toJSON(responseData));
    }
}
