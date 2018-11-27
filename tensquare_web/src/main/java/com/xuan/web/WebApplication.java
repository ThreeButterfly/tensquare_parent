package com.xuan.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/27 0027
 * @Time: 15:00
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class WebApplication {

    /**
     * 我阿康很强
     *
     * */
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class);
    }

}
