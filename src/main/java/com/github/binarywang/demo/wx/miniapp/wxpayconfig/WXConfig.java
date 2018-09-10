package com.github.binarywang.demo.wx.miniapp.wxpayconfig;

import java.util.Random;

public class WXConfig{

    /**
     * 公众号id
     */
    public static final String appid = "wx727375aee4a9f607";

    /**
     * 公众号密钥
     */
    public static final String appSecret = "a9cec7f35dc3eeda2f80535aabafa0af";

    /**
     * 商户号
     */
    public static final String mch_id = "1380897702";

    /**
     * 商户号密钥
     */
    public static final String paternerKey = "c883624dcb4f4fff94264357a0ed1b04";

    /**
     * 随机字符串
     */
    public static final String nonce_str = MD5.getMessageDigest(String.valueOf(new Random().nextInt(10000)).getBytes());

    /**
     * 时间戳
     */
    public static final String timeStamp = Long.toString(System.currentTimeMillis()/1000);

    /**
     * 签名方式
     */
    public static final String signType = "MD5";

    /**
     * 通知地址
     */
    //public static final String notify_url = "http://wxback.fhdream.com:8080/lib/wxpay/weiXinNotify";
    public static final String notify_url = "http://sell.fhdream.com:8080/lib/wxpay/weiXinNotify";

    /**
     * 交易类型
     */
    public static final String trade_type = "JSAPI";

    /**
     * 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
     */
    public static final String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 申请退款
     */
    public static final String refund_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";




}
