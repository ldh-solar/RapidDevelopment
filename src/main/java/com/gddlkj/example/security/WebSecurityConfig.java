package com.gddlkj.example.security;


import com.gddlkj.example.filter.JWTAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    JWTAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 公共资源请求列表
        String[] resources = new String[]{"/druid/**", "/swagger-ui.html", "/webjars/**", "/v2/**", "/swagger-resources/**"};

        // post匿名请求列表
        String[] postMapping = new String[]{"/admin/user/login","/admin/license/upload","/admin/user/verifyCode"};

        // get匿名请求列表
        String[] getMapping = new String[]{"/admin/file/download/**","/admin/license"};

        // 关闭csrf验证
        http.csrf().disable().cors()
                // create no session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 对请求进行认证
                .and()
                .authorizeRequests()
                .antMatchers(resources).permitAll()
                .antMatchers(HttpMethod.POST, postMapping).permitAll()
                .antMatchers(HttpMethod.GET, getMapping).permitAll()
                // 所有请求需要身份认证
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(
                        new Http401AuthenticationEntryPoint())
                .and()
                // 添加一个过滤器验证其他请求的Token是否合法
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
