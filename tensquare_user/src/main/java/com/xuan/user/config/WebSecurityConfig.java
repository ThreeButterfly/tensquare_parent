package com.xuan.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置类
 * @author Administrator
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //主路径不被拦截
                .antMatchers("/").permitAll()
                //拦截其他路径
                .anyRequest().authenticated()
                .and()
                //注销请求不被拦截
                .logout().permitAll()
                .and()
                //支持表单登录
                .formLogin();
        //关闭默认的csrf认证
        http.csrf().disable();
    }
}
