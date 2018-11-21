package com.xuan.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.xuan.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Resource
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    private String template_code;

    @Value("${aliyun.sms.sign_name}")
    private String sign_name;

    @RabbitHandler
    public void executeSms(Map<String, String> map) {
        String mobile = map.get("mobile");
        String checkCode = map.get("checkCode");
        System.out.println("手机号：" + mobile);
        System.out.println("验证码：" + checkCode);

        // try {
        //     smsUtil.sendSms(mobile, template_code, sign_name, "{\"checkCode\":\"" + checkCode + "\"}");
        // } catch (ClientException e) {
        //     e.printStackTrace();
        // }

    }
}
