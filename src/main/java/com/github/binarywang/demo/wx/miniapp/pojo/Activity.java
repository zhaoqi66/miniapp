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
import java.util.Date;

/**
 * Activity
 *
 * @author juan
 * @date 2018/8/28 15:06
 */
@Entity
@Table(name = "activity")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    /**
     * 活动主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "activity_id", unique = true)
    private int activityId;

    @Column(name = "activity_name", columnDefinition = "VARCHAR(32) COMMENT'活动名称'")
    private String activityName;

    @Column(name = "start_time", columnDefinition = "DATETIME COMMENT'活动开始时间'")
    private Date startTime;

    @Column(name = "end_time", columnDefinition = "DATETIME COMMENT'活动结束时间'")
    private Date endTime;

    @Column(name = "name", columnDefinition = "VARCHAR(1500) COMMENT'活动规则'")
    private String pic;

    @Column(name = "activity_take_count", columnDefinition = "INT(11) COMMENT'活动报名人数'")
    private int activityTakeCount;

    @Column(name = "activity_status", columnDefinition = "TINYINT(4) COMMENT'活动状态(0报名中1已结束2活动中)'")
    private String status;

    @Column(name = "delete_flag", columnDefinition = "TINYINT(4) COMMENT'删除标记(0删除1正常)'")
    private String deleteFlag;

    @Column(name = "create_time", columnDefinition = "DATETIME COMMENT'活动创建时间'")
    private Date createTime;

    @Column(name = "modified_time", columnDefinition = "DATETIME COMMENT'活动修改时间'")
    private Date modifiedTime;


}
