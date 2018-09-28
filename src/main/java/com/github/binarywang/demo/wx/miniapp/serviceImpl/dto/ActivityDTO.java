package com.github.binarywang.demo.wx.miniapp.serviceImpl.dto;

import com.github.binarywang.demo.wx.miniapp.pojo.Activity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * ActivityDTO
 *
 * @author juan
 * @date 2018/9/26 14:59
 */
@Data
@ApiModel(value = "活动返回参数")
public class ActivityDTO {

    @ApiModelProperty(value = "活动表主键")
    private int activityId;

    @ApiModelProperty(value = "活动名称")
    private String activityName;

    @ApiModelProperty(value = "活动规则")
    private String[] pic;

    @ApiModelProperty(value = "第一档金额")
    private double firstMoney;

    @ApiModelProperty(value = "第一档人气")
    private int firstRatio;

    @ApiModelProperty(value = "第二档金额")
    private double secondMoney;

    @ApiModelProperty(value = "第二档人气")
    private int secondRatio;

    @ApiModelProperty(value = "第三档金额")
    private double thirdMoney;

    @ApiModelProperty(value = "第三档人气")
    private int thirdRatio;

    public ActivityDTO() {
    }

    public ActivityDTO(Activity activity, String[] pic) {
        this.activityId = activity.getActivityId();
        this.activityName = activity.getActivityName();
        this.pic = pic;
        this.firstMoney = activity.getFirstMoney();
        this.firstRatio = activity.getFirstRatio();
        this.secondMoney = activity.getSecondMoney();
        this.secondRatio = activity.getSecondRatio();
        this.thirdMoney = activity.getThirdMoney();
        this.thirdRatio = activity.getThirdRatio();
    }
}
