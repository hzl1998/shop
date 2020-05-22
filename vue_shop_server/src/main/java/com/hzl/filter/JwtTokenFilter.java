package com.hzl.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzl.service.UserService;
import com.hzl.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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
    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if ("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        if (!httpServletRequest.getRequestURI().equals("/shop/login")) {

            String token = httpServletRequest.getHeader("Authorization");
            if (token == null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("code", 400);//400
                map.put("msg", "请先登录获取token");
                httpServletResponse.setContentType("UTF-8");
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(httpServletResponse.getOutputStream(), map);
                } catch (Exception a) {
                    throw new ServletException();
                }
            } else {
                Claims claims = JwtUtils.checkJWT(token);
                if (claims == null) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("code", 400);//400
                    map.put("msg", "无效token");
                    httpServletResponse.setContentType("application/json");
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.writeValue(httpServletResponse.getOutputStream(), map);
                    } catch (Exception a) {
                        throw new ServletException();
                    }
                }
                String username = (String) claims.get("username");
                UserDetails userDetails = userService.loadUserByUsername(username);
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    //设置为已登录
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
