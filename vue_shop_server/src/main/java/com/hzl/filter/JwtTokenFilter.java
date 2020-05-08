package com.hzl.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzl.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (!httpServletRequest.getRequestURI().equals("/shop/login")){

        String token = httpServletRequest.getHeader("Authorization");
        if (token == null){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", "400");//400
            map.put("message", "请先登录获取token");
            httpServletResponse.setContentType("UTF-8");
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(httpServletResponse.getOutputStream(), map);
            } catch (Exception a) {
                throw new ServletException();
            }
        }
        else {
            if (JwtUtils.checkJWT(token) == null){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("code", "400");//400
                map.put("msg", "无效token");
                httpServletResponse.setContentType("application/json");
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(httpServletResponse.getOutputStream(), map);
                } catch (Exception a) {
                    throw new ServletException();
                }
            }
        }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
