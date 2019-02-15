package com.jkfq.authserver.error;

import com.alibaba.fastjson.JSON;
import com.jkfq.authserver.entity.ResponseData;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class CustomAccessDeineHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("text/javascript;charset=utf-8");
        ResponseData responseData = new ResponseData();
        responseData.setCode(403);
        responseData.setMessage("认证用户--没有访问权限!");
        httpServletResponse.getWriter().print(JSON.toJSON(responseData));

    }
}
