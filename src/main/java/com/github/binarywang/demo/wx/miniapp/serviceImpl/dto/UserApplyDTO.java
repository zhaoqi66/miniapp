package com.github.binarywang.demo.wx.miniapp.serviceImpl.dto;

import com.github.binarywang.demo.wx.miniapp.pojo.UserApply;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * UserApplyDTO
 *
 * @author juan
 * @date 2018/8/30 9:33
 */
@Data
@ApiModel(value = "报名详情接口返回参数")
public class UserApplyDTO {

    @ApiModelProperty(value = "活动报名表主键")
    private Long id;

    @ApiModelProperty(value = "活动参与者姓名")
    private String name;

    @ApiModelProperty(value = "性别 0-女 1-男")
    private String gerder;

    @ApiModelProperty(value = "报名电话")
    private String phone;

    @ApiModelProperty(value = "个人介绍")
    private String description;

    @ApiModelProperty(value = "照片")
    private String[] pic;

    @ApiModelProperty(value = "活动id")
    private Integer activityId;

    @ApiModelProperty(value = "注册用户id")
    private String openId;

    @ApiModelProperty(value = "报名状态")
    private String status;

    public UserApplyDTO() {
    }

    public UserApplyDTO(UserApply userApply, String[] pic) {
        this.id = userApply.getId();
        this.name = userApply.getName();
        this.gerder = userApply.getGerder();
        this.phone = userApply.getPhone();
        this.description = userApply.getDescription();
        this.pic = pic;
        this.activityId = userApply.getActivityId();
        this.openId = userApply.getOpenId();
        this.status = userApply.getStatus();
    }
}
