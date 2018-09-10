package com.github.binarywang.demo.wx.miniapp.controller.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClickVoteVm
 *
 * @author juan
 * @date 2018/9/7 10:34
 */
@Data
@ApiModel(value = "投人气按钮参数")
public class ClickVoteVm {

    @ApiModelProperty(value = "用户的openId",required = true)
    private String openId;

    @ApiModelProperty(value = "票数",required = true)
    private int tickets;

    @ApiModelProperty(value = "参赛选手id",required = true)
    private int id;
}
