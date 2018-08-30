package com.github.binarywang.demo.wx.miniapp.controller.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * UserApplyDetailVm
 *
 * @author juan
 * @date 2018/8/30 10:32
 */
@Data
@ApiModel(value = "查询选手详情参数")
public class UserApplyDetailVm {

    @ApiModelProperty(value = "主键id")
    private long id;

    @ApiModelProperty(value = "选手姓名")
    private String name;
}
