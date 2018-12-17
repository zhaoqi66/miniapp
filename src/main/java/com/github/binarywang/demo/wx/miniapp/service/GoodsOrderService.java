package com.github.binarywang.demo.wx.miniapp.service;

import com.github.binarywang.demo.wx.miniapp.controller.vm.GoodsOrderVm;
import org.jdom.JDOMException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author FHZD.xiaoxun
 * @date 2018/12/10
 */
public interface GoodsOrderService {
    String createOrder(GoodsOrderVm goodsOrderVm);

    void deleteOrder(String orderId);

    Map payGoodsOrder(String orderId, HttpServletRequest request);

    void wxPayBack(HttpServletResponse response, HttpServletRequest request) throws IOException, JDOMException;
}
