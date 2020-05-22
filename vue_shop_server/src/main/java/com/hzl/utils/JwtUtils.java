package com.hzl.utils;

import com.hzl.entity.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class JwtUtils {
    public static final long EXPIRE_TIME = 1000*60*60*24*7;  //过期时间，毫秒，一周
    public static final String TOKEN_SECRET = "hzl666";

    public static String geneJsonWebToken(UserDto user) {
        if (user != null && user.getId() != null && user.getUsername() != null) {
            String token = Jwts.builder().setSubject("sub").claim("id", user.getId()).claim("username", user.getUsername()).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 604800000L)).signWith(SignatureAlgorithm.HS256, "hzl666").compact();
            return token;
        } else {
            return null;
        }
    }

    public static Claims checkJWT(String token) {
        try {
            Claims claims = (Claims)Jwts.parser().setSigningKey("hzl666").parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception var2) {
            return null;
        }
    }

    public static void main(String[] args) {

        Claims claims = (Claims)Jwts.parser().setSigningKey("hzl666").parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdWIiLCJpZCI6IjQ4NTUwZTIyOTEwMjExZWE5ZTI1ODBmYTViM2U4NTI2IiwidXNlcm5hbWUiOiJhZG1pbiIsImlhdCI6MTU5MDE1NDE2NywiZXhwIjoxNTkwNzU4OTY3fQ.X1LVFxrTfvfhYuH2WKMLo_nuMsOfcFXIki3zWafGKzI").getBody();
        System.out.println(claims.get("username"));
    }
}
