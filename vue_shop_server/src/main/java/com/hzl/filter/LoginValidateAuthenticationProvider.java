package com.hzl.filter;

import com.hzl.entity.UserDto;
import com.hzl.mapper.UserMapper;
import com.hzl.service.UserService;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.hzl.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class LoginValidateAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    public LoginValidateAuthenticationProvider() {
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String rawPassword = (String)authentication.getCredentials();
        if (username != null && !username.equals("")) {
            if (rawPassword != null && !rawPassword.equals("")) {
                UserDetails user = this.userService.loadUserByUsername(username);
                if (user == null) {
                    throw new BadCredentialsException("用户不存在！");
                } else {
                    UserDto userDto = this.userMapper.getUserByUserName(username);
                    if (!user.isEnabled()) {
                        throw new DisabledException("该账户已被禁用，请联系管理员");
                    } else if (!user.isAccountNonLocked()) {
                        throw new LockedException("该账号已被锁定");
                    } else if (!user.isAccountNonExpired()) {
                        throw new AccountExpiredException("该账号已过期，请联系管理员");
                    } else if (!user.isCredentialsNonExpired()) {
                        throw new CredentialsExpiredException("该账户的登录凭证已过期，请重新登录");
                    } else {
                        try {
                            if (!MD5.validPassword(rawPassword, user.getPassword(), userDto.getSalt())) {
                                throw new BadCredentialsException("输入密码错误!");
                            }
                        } catch (NoSuchAlgorithmException var7) {
                            var7.printStackTrace();
                        } catch (UnsupportedEncodingException var8) {
                            var8.printStackTrace();
                        }

                        return new UsernamePasswordAuthenticationToken(user, rawPassword, user.getAuthorities());
                    }
                }
            } else {
                throw new BadCredentialsException("密码不能为空！");
            }
        } else {
            throw new BadCredentialsException("用户名不能为空！");
        }
    }

    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}