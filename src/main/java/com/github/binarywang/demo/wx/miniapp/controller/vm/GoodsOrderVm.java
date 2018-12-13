package com.github.binarywang.demo.wx.miniapp.controller.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author FHZD.xiaoxun
 * @date 2018/12/10
 */
@Data
@ApiModel(value = "创建商品订单参数")
public class GoodsOrderVm {
    @ApiModelProperty(value = "商品Id")
    @NotNull(message = "商品ID必传")
    private String goodsId;

    @ApiModelProperty(value = "商品购买数量")
    @NotNull(message = "购买数量必传")
    private Integer orderGoodsNumber;

    @ApiModelProperty(value = "收货人姓名")
    @NotNull(message = "收货人姓名必传")
    private String shippingCustomerName;

    @ApiModelProperty(value = "收货人联系方式")
    @NotNull(message = "收货人联系方式必传")
    private String shippingCustomerTelephone;

    @ApiModelProperty(value = "物流方式：0、物流配送，1、自提")
    @NotNull(message = "物流方式必传")
    private String shippingType;

    @ApiModelProperty(value = "收货详细地址")
    @NotNull(message = "收货详细地址必传")
    private String shippingAreaDetail;

    @ApiModelProperty(value = "支付类型：1、在线支付，2、货到付款")
    private String orderLinePay;

    @ApiModelProperty(value = "用户的openID")
    @NotNull(message = "用户的openID必填")
    private String openId;

}
