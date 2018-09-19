package com.github.binarywang.demo.wx.miniapp.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.github.binarywang.demo.wx.miniapp.pojo.VoteUser;
import com.github.binarywang.demo.wx.miniapp.service.VoteUserService;
import com.github.binarywang.demo.wx.miniapp.utils.JsonUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sun.awt.image.URLImageSource;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信小程序用户接口
 *
 * @author
 */
@RestController
@RequestMapping("/wechat/user")
public class WxMaUserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxMaService wxService;

    @GetMapping("/getToken")
    public String TestRest(){

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxb3596b2d855782b7&secret=4229d31f90241c2a8f5f40a61a9c10ee";
        return restTemplate.getForObject(url,String.class);

    }


    @Autowired
    private VoteUserService voteUserService;
    /**
     * 登陆接口
     */
    @GetMapping("/login")
    public String login(String code) {
        if (StringUtils.isBlank(code)) {
            return "empty jscode";
        }

        try {
            WxMaJscode2SessionResult session = this.wxService.getUserService().getSessionInfo(code);
            this.logger.info(session.getSessionKey());
            this.logger.info(session.getOpenid());

            //TODO 可以增加自己的逻辑，关联业务相关数据
            VoteUser voteUser =voteUserService.findOneByOpenid(session.getOpenid());
            if (voteUser !=null ){
                return JsonUtils.toJson(session);
            }else {
                VoteUser user = new VoteUser();
                user.setOpenid(session.getOpenid());
                user.setFreetickets(1);
                user.setTickets(0);
                user.setStartTime(new Date());
                voteUserService.save(user);
            }
            return JsonUtils.toJson(session);
        } catch (WxErrorException e) {
            this.logger.error(e.getMessage(), e);
            return e.toString();
        }
    }


    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @GetMapping("/info")
    public String info(String sessionKey, String signature, String rawData, String encryptedData, String iv) {
        // 用户信息校验
        if (!this.wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密用户信息
        WxMaUserInfo userInfo = this.wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);

        return JsonUtils.toJson(userInfo);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/phone")
    public String phone(String sessionKey, String signature, String rawData, String encryptedData, String iv) {
        // 用户信息校验
        if (!this.wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = this.wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        return JsonUtils.toJson(phoneNoInfo);
    }

}
