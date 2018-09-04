package com.github.binarywang.demo.wx.miniapp.service;

import com.github.binarywang.demo.wx.miniapp.pojo.VoteUser;

public interface VoteUserService {

    public void save(VoteUser user);

    int findOneByOpenid(String openid);
}
