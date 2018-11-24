package com.xuan.recruit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * <p>Description: 招聘微服务启动类 </p>
 * @author Yat-Xuan
 * @Date: 2018/11/14 0014 12:54
 * @Modified By:
*/
@SpringBootApplication
@EnableEurekaClient
public class RecruitApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecruitApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }

}
