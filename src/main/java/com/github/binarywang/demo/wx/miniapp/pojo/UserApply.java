package com.github.binarywang.demo.wx.miniapp.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * UserApply
 *
 * @author juan
 * @date 2018/8/28 15:21
 */
@Entity
@Table(name = "user_apply")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserApply implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private long id;

    @Column(name = "name", columnDefinition = "VARCHAR(16) COMMENT'活动参与者姓名'")
    private String name;

    @Column(name = "gerder", columnDefinition = "VARCHAR(3) COMMENT'性别 0-女 1-男'")
    private String gerder;

    @Column(name = "phone", columnDefinition = "VARCHAR(11) COMMENT'报名电话'")
    private String phone;

    @Column(name = "description", columnDefinition = "VARCHAR(128) COMMENT'个人介绍'")
    private String description;

    @Column(name = "pic", columnDefinition = "VARCHAR(1000) COMMENT'照片'")
    private String pic;

    @Column(name = "total_votes", columnDefinition = "BIGINT(11) COMMENT'总票数'")
    private long totalVotes;

    @Column(name = "apply_time", columnDefinition = "DATETIME COMMENT'报名参加活动时间'")
    private Date applyTime;

    @Column(name = "review_time", columnDefinition = "DATETIME COMMENT'管理者审核时间'")
    private Date reviewTime;

    @Column(name = "update_time", columnDefinition = "DATETIME COMMENT'修改报名信息时间'")
    private Date updateTime;

    @Column(name = "status", columnDefinition = "VARCHAR(3) COMMENT'报名状态0-已报名 1-取消报名 2-报名成功(审核通过) 3-审核不通过'")
    private String status;

    @Column(name = "activity_id", columnDefinition = "INT(11) COMMENT'活动id'")
    private Integer activityId;

    @Column(name = "open_id", columnDefinition = "VARCHAR(64) COMMENT'注册用户id'")
    private String openId;

    @Column(name = "reason", columnDefinition = "VARCHAR(128) COMMENT'审核原因'")
    private String reason;


}
