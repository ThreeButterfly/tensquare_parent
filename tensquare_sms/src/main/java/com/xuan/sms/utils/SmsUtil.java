package com.xuan.sms.utils;

import com.wxapi.WxApiCall.WxApiCall;
import com.wxapi.model.RequestModel;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;

/**
 * 短信工具类
 *
 * @author Administrator
 */
@Component
public class SmsUtil {

    private String appkey = "3a45ef6c2f4f4cbb24210b74a65313f3";
    private String gwUrl = "https://way.jd.com/chuangxin/dxjk";

    public void sendSms(String mobile, String checkCode) {
        RequestModel model = new RequestModel();
        model.setGwUrl(gwUrl);
        model.setAppkey(appkey);
        Map queryMap = new HashMap();
        queryMap.put("mobile", mobile);
        queryMap.put("content", "【创信】你的验证码是：" + checkCode + "，3分钟内有效！");
        model.setQueryParams(queryMap);
        WxApiCall call = new WxApiCall();
        call.setModel(model);
        call.request();
    }
}
