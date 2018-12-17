package com.github.binarywang.demo.wx.miniapp.controller;

import com.github.binarywang.demo.wx.miniapp.config.ResponseModel;
import com.github.binarywang.demo.wx.miniapp.config.ResponseModels;
import com.github.binarywang.demo.wx.miniapp.controller.vm.GoodsOrderVm;
import com.github.binarywang.demo.wx.miniapp.service.GoodsOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

/**
 * @author FHZD.xiaoxun
 * @date 2018/12/10
 */
@RestController
@RequestMapping("miniApi")
@Api(description = "用户购买模块")
public class GoodsOrderController {
    private static final Logger logger = LoggerFactory.getLogger(GoodsOrderController.class);

    @Autowired
    private GoodsOrderService goodsOrderService;

    @PutMapping("/order/createOrder")
    @ApiOperation(value = "订单生成接口")
    public ResponseModel createOrder(@Valid @RequestBody GoodsOrderVm goodsOrderVm) {
        logger.info("商品订单生成接口 goodsOrderVm={}", goodsOrderVm);
        String  orderShowId = goodsOrderService.createOrder(goodsOrderVm);
        return ResponseModels.ok(orderShowId);
    }

    @DeleteMapping("/order/deleteOrder")
    @ApiOperation(value = "取消订单接口")
    public ResponseModel deleteOrder(@RequestParam(name = "orderId", required = true) String orderId) {
        logger.info("取消订单接口 orderId={}", orderId);
        goodsOrderService.deleteOrder(orderId);
        return ResponseModels.ok();
    }

    @PostMapping("/order/payGoodsOrder")
    @ApiOperation(value = "订单支付接口")
    public ResponseModel payGoodsOrder(@RequestParam(name = "orderId", required = true) String orderId, HttpServletRequest request) {
        logger.info("订单支付接口 orderId={}", orderId);
        Map map = goodsOrderService.payGoodsOrder(orderId, request);
        return ResponseModels.ok(map);
    }

    @PostMapping("/order/wxPayBack")
    @ApiOperation(value = "订单支付完成回调接口")
    public ResponseModel wxPayBack(HttpServletRequest request, HttpServletResponse response) throws IOException, JDOMException {
        logger.info("订单支付完成回调接口 ");
        goodsOrderService.wxPayBack(response, request);
        return ResponseModels.ok();

    }

}
