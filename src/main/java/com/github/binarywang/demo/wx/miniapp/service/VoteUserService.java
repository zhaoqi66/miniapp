package com.github.binarywang.demo.wx.miniapp.service;

import com.github.binarywang.demo.wx.miniapp.controller.vm.ClickVoteVm;
import com.github.binarywang.demo.wx.miniapp.controller.vm.VoteOrderAddVm;
import com.github.binarywang.demo.wx.miniapp.pojo.VoteUser;
import org.jdom.JDOMException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface VoteUserService {

    public void save(VoteUser user);

    int findOneByOpenid(String openid);

    void deleteFreeTicketTask();

    Map getFreeVote(String openId);

    void clickVote(ClickVoteVm vm);

    int createVoteOrder(VoteOrderAddVm vm);

    Map payVoteOrder(int orderId, HttpServletRequest request);

    void getWXPayBack(HttpServletResponse response, HttpServletRequest request) throws IOException, JDOMException;
}
