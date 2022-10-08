package com.athuang.msmservice.utils;

/**
 * @program: atcrowdfunding01-admin-parent
 *
 * @description: 短信类
 *
 * @author: Mr.Huang
 *
 * @create: 2022-07-29 14:44
 **/

import com.alibaba.fastjson.JSONObject;

import com.zhenzi.sms.ZhenziSmsClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author cb
 * @Date 2021-11-25 10:06
 **/
@Component
@ConfigurationProperties(prefix = "zhenzi")
public class ZhenZiYunSMSUtils {

    private String apiUrl;          // apiUrl
    private String appId;           // 应用id
    private String appSecret;       // 应用secret
    private String templateId;      // 模板id
    private String invalidTimer;    // 失效时间
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 发送短信验证码
     *
     * @param telNumber 接收者手机号码
     *         随机验证码（四位或六位）
     * @return
     */
    public boolean sendSMS(String param,String telNumber) throws Exception {
        // 榛子云短信 客户端
        // 请求地址，个人开发者使用https:// sms_developer.zhenzikj.com，企业开发者使用https:// sms.zhenzikj.com
        ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
        // 存放请求参数的map集合
        Map<String, Object> params = new HashMap<String, Object>();
        // 接收者手机号码
        params.put("number", telNumber);
        // 短信模板ID
        params.put("templateId", templateId);
        // 短信模板参数
        String[] templateParams = new String[2];

//         StringBuilder builder = new StringBuilder();

//         for (int i = 0 ; i < 4 ; i++)
//         {
//             int ramdom = (int)(Math.random() * 10);
//             builder.append(ramdom);
//         }
//         String validateCode =  builder.toString();
        String validateCode = param;
        setCode(validateCode);

        templateParams[0] = validateCode;
        templateParams[1] = invalidTimer;
        params.put("templateParams", templateParams);
        /**
         * 1.send方法用于单条发送短信,所有请求参数需要封装到Map中;
         * 2.返回结果为json串：{ "code":0,"data":"发送成功"}
         * 3.备注：（code: 发送状态，0为成功。非0为发送失败，可从data中查看错误信息）
         */
        String result = client.send(params);

        JSONObject jsonObject = JSONObject.parseObject(result);

//         System.out.println(jsonObject.getString("code"));

        if(jsonObject.getString("code").equals("0")){

            return true;

        }else{

            return false;
        }



    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getInvalidTimer() {
        return invalidTimer;
    }

    public void setInvalidTimer(String invalidTimer) {
        this.invalidTimer = invalidTimer;
    }
}
