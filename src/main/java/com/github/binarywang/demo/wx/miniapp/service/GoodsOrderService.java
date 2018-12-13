package com.github.binarywang.demo.wx.miniapp.service;

import com.github.binarywang.demo.wx.miniapp.controller.vm.GoodsOrderVm; /**
 * @author FHZD.xiaoxun
 * @date 2018/12/10
 */
public interface GoodsOrderService {
    String createOrder(GoodsOrderVm goodsOrderVm);

    void deleteOrder(String orderId);
}
