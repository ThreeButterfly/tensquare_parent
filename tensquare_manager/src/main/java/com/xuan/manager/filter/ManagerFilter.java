package com.xuan.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/27 0027
 * @Time: 16:07
 */
@Component
public class ManagerFilter extends ZuulFilter {

    @Resource
    private JwtUtil jwtUtil;

    /**
     * 在请求前pre或者请求后post执行
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 多个过滤器的执行顺序，数字越小，越先执行
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 当前过滤是否开启，true-开启 false-关闭
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器内执行的操作
     * setsendzullRespponse(false) 表示不继续执行
     *
     * @return 表示任何object的值都继续执行
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("经过后台过滤器了！");

        //得到request的上下文
        RequestContext requestContext =
                RequestContext.getCurrentContext();

        // 得到request域
        HttpServletRequest request = requestContext.getRequest();

        final String options = "OPTIONS";
        if (options.equals(request.getMethod())) {
            return null;
        }

        //判断请求为登录页面，直接放行
        String url = request.getRequestURL().toString();
        String urlFlag = "/admin/login";
        if (url.indexOf(urlFlag) > 0) {
            System.out.println("登陆页面" + url);
            return null;
        }

        // 得到头部信息
        String header = request.getHeader("Authorization");

        final String Bearer="Bearer ";

        //判断请求头是否存在
        if (!StringUtils.isBlank(header) && header.startsWith(Bearer)) {
            // 如果请求头存在，并且是以 (Bearer )开头，提取token
            final String token = header.substring(7);

            try {
                //解析token
                Claims claims = jwtUtil.parseJWT(token);

                //获取角色
                String role = (String) claims.get("roles");

                if (role != null) {
                    if ("admin".equals(role)) {
                        // 若有值，继续向下传递
                        requestContext.addZuulRequestHeader("Authorization", header);
                        return null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

                //终止运行
                requestContext.setSendZuulResponse(false);
            }
        }

        //终止运行
        requestContext.setSendZuulResponse(false);
        //http状态码
        requestContext.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        requestContext.setResponseBody("{message:无权访问}");
        requestContext.getResponse().setContentType("application/json;charset=UTF-8");
        return null;
    }
}
