package com.hzl.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
    public AuthExceptionEntryPoint() {
    }

    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException a) throws IOException, ServletException {
        Map<String, Object> map = new HashMap();
        map.put("code", "401");
        map.put("message", "请先登录");
        httpServletResponse.setContentType("UTF-8");

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(httpServletResponse.getOutputStream(), map);
        } catch (Exception var6) {
            throw new ServletException();
        }
    }
}
