package com.hzl.config;

import com.hzl.filter.JwtTokenFilter;
import com.hzl.filter.LoginValidateAuthenticationProvider;
import com.hzl.handler.AuthAccessDeniedHandler;
import com.hzl.handler.AuthAuthenticationFailureHandler;
import com.hzl.handler.AuthAuthenticationSuccessHandler;
import com.hzl.handler.AuthExceptionEntryPoint;
import com.hzl.handler.AuthLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.MultipartConfigElement;
import java.io.File;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AuthExceptionEntryPoint authExceptionEntryPoint;
    @Autowired
    AuthAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    AuthAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    AuthLogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    AuthAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private LoginValidateAuthenticationProvider loginValidateAuthenticationProvider;
    @Autowired
    JwtTokenFilter jwtTokenFilter;

    //安全拦截机制
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //屏蔽CSRF控制
                .httpBasic().authenticationEntryPoint(authExceptionEntryPoint)
                .and()
                //开启跨域
                .cors()
                .and()
                .authorizeRequests()
                //处理跨域请求中的Preflight请求
                .requestMatchers( CorsUtils::isPreFlightRequest ).permitAll()
                .anyRequest().authenticated()//所有的请求必须认证通过
                .antMatchers("/hello").permitAll()//登录请求不需要认证
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
        ;
        http.logout().logoutSuccessHandler(logoutSuccessHandler);
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        http.authorizeRequests().anyRequest().permitAll().and().logout().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.loginValidateAuthenticationProvider);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String tmpPath = System.getProperty("user.dir") + "/data/tmp";
        System.out.println(tmpPath);
        File file = new File(tmpPath);
        if (!file.exists()) {
            file.mkdir();
        }
        factory.setLocation(tmpPath);
        return factory.createMultipartConfig();
    }
}