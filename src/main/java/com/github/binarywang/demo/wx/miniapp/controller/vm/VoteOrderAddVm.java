package com.github.binarywang.demo.wx.miniapp.controller.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * VoteOrderAddVm
 *
 * @author juan
 * @date 2018/9/10 10:13
 */
@Data
@ApiModel(value = "创建订单参数")
public class VoteOrderAddVm {

    @ApiModelProperty(value = "金额")
    @NotNull(message = "金额必传")
    private double orderMoney;

    @ApiModelProperty(value = "人气数")
    @NotNull(message = "人气数必传")
    private int votePoll;

    @ApiModelProperty(value = "活动id")
    @NotNull(message = "活动id必传")
    private int activityId;

    @ApiModelProperty(value = "用户的openID")
    @NotNull(message = "用户的openID必填")
    private String openId;


}
