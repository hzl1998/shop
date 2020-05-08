package com.hzl.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthAuthenticationFailureHandler implements AuthenticationFailureHandler {
    public AuthAuthenticationFailureHandler() {
    }

    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Map<String, Object> map = new HashMap();
        map.put("code", "400");
        map.put("message", "登录失败:" + e.getMessage());
        httpServletResponse.setContentType("UTF-8");

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(httpServletResponse.getOutputStream(), map);
        } catch (Exception var6) {
            throw new ServletException();
        }
    }
}