package com.jkfq.authserver.error;

import com.alibaba.fastjson.JSON;
import com.jkfq.authserver.entity.ResponseData;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ${DESCRIPTION}
 * AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
 * @Author
 * @create 2019-02-12
 **/
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("text/javascript;charset=utf-8");
        ResponseData responseData = new ResponseData();
        responseData.setCode(401);
        responseData.setMessage("匿名用户--没有访问权限!");
        httpServletResponse.getWriter().print(JSON.toJSON(responseData));
    }
}
