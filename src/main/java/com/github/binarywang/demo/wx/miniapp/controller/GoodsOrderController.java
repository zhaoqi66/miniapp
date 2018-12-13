package com.github.binarywang.demo.wx.miniapp.controller;

import com.github.binarywang.demo.wx.miniapp.config.ResponseModel;
import com.github.binarywang.demo.wx.miniapp.config.ResponseModels;
import com.github.binarywang.demo.wx.miniapp.controller.vm.GoodsOrderVm;
import com.github.binarywang.demo.wx.miniapp.service.GoodsOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

}
