package com.xuan.customer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/19 0019
 * @Time: 10:56
 */
@RabbitListener(queues = "itcast")
@Component
public class CustomerOne {

    @RabbitHandler
    public void getMsg(String msg) {
        System.out.println("直接模式下消费： " + msg);
    }
}
