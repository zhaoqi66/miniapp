package com.github.binarywang.demo.wx.miniapp.utils;


import com.github.binarywang.demo.wx.miniapp.config.BusinessException;
import lombok.Data;

import java.util.regex.Pattern;

/**
 * SendSmsReq
 *
 * @author juan
 * @date 2018/8/29 16:11
 */
@Data
public class SendSmsReq {
    //以下静态变量，需要到阿里云控台-大于短信控制台地方添加获取
    public static final String SIGN_NAME = "制霸AMC";
    public static final String REGISTER_TEMPLATE_CODE = "SMS_113660361"; //注册短信模板
    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^1[34578]\\d{9}$";

    private String phone; //手机号
    private String signName; //短信签名
    private String templateCode; //短信模板
    private String templateParam; //短信模板参数

    public SendSmsReq() {
    }

    public SendSmsReq(String phone, String signName, String templateCode, String templateParam) {
        if (phone == null) {
            throw new BusinessException("手机号号码必传");
        }
        //校验手机格式
        if (isMobile(phone)) {
            throw new BusinessException("手机号码格式不正确");
        }
        if (templateParam == null) {
            throw new BusinessException("模板参数不能为空！");
        }

        this.phone = phone;
        this.signName = isEmpty(signName) ? SIGN_NAME : signName;
        this.templateCode = isEmpty(templateCode) ? REGISTER_TEMPLATE_CODE : templateCode;
        this.templateParam = templateParam;
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    private static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    protected Boolean isEmpty(String aString) {
        if (null == aString || aString.trim().isEmpty()) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }


}
