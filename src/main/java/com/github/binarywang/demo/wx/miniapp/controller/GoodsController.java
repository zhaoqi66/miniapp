package com.github.binarywang.demo.wx.miniapp.controller;

import com.github.binarywang.demo.wx.miniapp.config.ResponseModel;
import com.github.binarywang.demo.wx.miniapp.config.ResponseModels;
import com.github.binarywang.demo.wx.miniapp.pojo.Goods;
import com.github.binarywang.demo.wx.miniapp.service.GoodsService;
import com.github.binarywang.demo.wx.miniapp.serviceImpl.dto.GoodsCommendDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FHZD.xiaoxun
 * @date 2018/12/10
 */
@RestController
@RequestMapping("miniApi")
@Api(description = "商品展示模块")
public class GoodsController {
    private static final Logger logger = LoggerFactory.getLogger(GoodsOrderController.class);

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/goods/commend")
    @ApiOperation(value = "推荐商品接口")
    public ResponseModel commend() {
        logger.info("推荐商品接口");
        ArrayList<GoodsCommendDTO> goods = goodsService.list(null,0,4);
        return ResponseModels.ok(goods);
    }

    @GetMapping("/goods/list")
    @ApiOperation(value = "商品列表接口")
    public ResponseModel list(@RequestParam(name = "goodsName", required = false) String goodsName,
                              @RequestParam(name = "pageNumber", required = true) int pageNumber,
                              @RequestParam(name = "pageSize", required = true) int pageSize) {
        logger.info("商品分页查询接口  goodsName={} pageNumber={},pageSize={}", goodsName, pageNumber, pageSize);
        ArrayList<GoodsCommendDTO> list = goodsService.list(goodsName, pageNumber, pageSize);
        return ResponseModels.ok(list);
    }
}
