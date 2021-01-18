package com.gddlkj.example.security;

import com.gddlkj.example.model.constants.ResponseCodeEnum;
import com.gddlkj.example.model.dto.R;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Http401AuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(new R<>(ResponseCodeEnum.UNAUTHORIZED.getCode(), ResponseCodeEnum.UNAUTHORIZED.getMessage(), null).fillString());
    }

}
