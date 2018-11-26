package com.xuan.friend.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/23 0023
 * @Time: 10:31
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("进入拦截器");
        //获取请求头中的信息
        final String header = request.getHeader("Authorization");

        if (header != null && !"".equals(header)) {
            if (header.startsWith("Bearer ")) {
                //提取token
                final String token = header.substring(7);

                //解析token
                try {
                    Claims claims = jwtUtil.parseJWT(token);

                    //获取角色
                    String role = (String) claims.get("roles");

                    if (role != null) {
                        if (("user").equals(role)) {
                            request.setAttribute("claims_user", claims);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException("令牌有误");
                }
            }
        }
        return true;
    }
}
