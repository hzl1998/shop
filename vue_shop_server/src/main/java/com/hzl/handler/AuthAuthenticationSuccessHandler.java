package com.hzl.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzl.entity.UserDto;
import com.hzl.mapper.UserMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hzl.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserMapper userMapper;

    public AuthAuthenticationSuccessHandler() {
    }

    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Object object = authentication.getPrincipal();
        if (object instanceof UserDetails) {
            UserDetails userDetails = (UserDetails)object;
            UserDto user = this.userMapper.getUserByUserName(userDetails.getUsername());
            String token = JwtUtils.geneJsonWebToken(user);
            Map<String, Object> map1 = new HashMap();
            map1.put("id", user.getId());
            map1.put("username", user.getUsername());
            map1.put("phone", user.getPhone());
            map1.put("token", token);
            Map<String, Object> map = new HashMap();
            map.put("code", 200);
            map.put("message", "登录成功");
            map.put("data", map1);
            httpServletResponse.setContentType("application/json");

            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(httpServletResponse.getOutputStream(), map);
            } catch (Exception var11) {
                throw new ServletException();
            }
        }

    }
}
