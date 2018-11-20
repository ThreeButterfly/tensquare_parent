package com.xuan.sms.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/20 0020
 * @Time: 10:45
 */
@RabbitListener(queues = "sms")
@Component
public class SmsListener {

    @RabbitHandler
    public void executeSms(Map<String, String> map){
        System.out.println("手机号："+map.get("mobile"));
        System.out.println("验证码："+map.get("checkCode"));
    }
}
