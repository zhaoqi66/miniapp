package com.github.binarywang.demo.wx.miniapp.controller.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * UserVoteAddVm
 *
 * @author juan
 * @date 2018/8/29 16:14
 */
@Data
@ApiModel(value = "我要报名接口")
public class UserVoteAddVm {

    @ApiModelProperty(value = "活动参与者姓名")
    @NotNull(message = "活动参与者姓名必填")
    private String name;

    @ApiModelProperty(value = "性别 0-女 1-男")
    @NotNull(message = "性别必填")
    private String gerder;

    @ApiModelProperty(value = "报名电话")
    @NotNull(message = "报名电话必填")
    private String phone;

    @ApiModelProperty(value = "个人介绍")
    @NotNull(message = "个人介绍必填")
    private String description;

    @ApiModelProperty(value = "照片")
    @NotNull(message = "照片必填")
    private String[] pic;

    @ApiModelProperty(value = "活动id")
    @NotNull(message = "活动id必填")
    private Integer activityId;

    @ApiModelProperty(value = "注册用户id")
    @NotNull(message = "注册用户id必填")
    private String openId;

    @ApiModelProperty(value = "手机验证码")
    @NotNull(message = "手机验证码必填")
    private String code;

}
