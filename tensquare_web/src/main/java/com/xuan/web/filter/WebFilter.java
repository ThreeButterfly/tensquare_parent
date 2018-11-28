package com.xuan.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/28 0028
 * @Time: 8:22
 */
public class WebFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("经过前台过滤器");

        //得到request的上下文
        RequestContext requestContext =
                RequestContext.getCurrentContext();
        // 得到request域
        HttpServletRequest request = requestContext.getRequest();
        // 得到头部信息
        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            // 若有值，继续向下传递
            requestContext.addZuulRequestHeader("Authorization", authorization);
        }
        return null;
    }
}
