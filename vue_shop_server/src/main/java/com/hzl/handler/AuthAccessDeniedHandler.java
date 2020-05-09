package com.hzl.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthAccessDeniedHandler implements AccessDeniedHandler {

    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        Map<String, Object> map = new HashMap();
        map.put("code", 403);
        map.put("msg", "无权访问");
        httpServletResponse.setContentType("UTF-8");

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(httpServletResponse.getOutputStream(), map);
        } catch (Exception var6) {
            throw new ServletException();
        }
    }
}
