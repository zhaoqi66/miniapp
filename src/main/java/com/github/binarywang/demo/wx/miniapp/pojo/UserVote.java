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
 * UserVote
 *
 * @author juan
 * @date 2018/9/5 10:09
 */
@Entity
@Table(name = "user_vote")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "end_time", columnDefinition = "DATETIME COMMENT'活动结束时间'")
    private Date endTime;

    @Column(name = "start_time", columnDefinition = "DATETIME COMMENT'登陆时间'")
    private Date startTime;

    @Column(name = "free_tickets",columnDefinition = "INT(11) COMMENT'免费票数'")
    private int freeTickets;

    @Column(name = "tickets", columnDefinition = "INT(4) COMMENT'充值票数'")
    private int tickets;

    @Column(name = "openid", columnDefinition = "VARCHAR(64) COMMENT'投票人员'")
    private String openid;
}
