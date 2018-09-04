package com.github.binarywang.demo.wx.miniapp.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "VoteUser")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteUser {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, columnDefinition = "INT(11) COMMENT'主键'")
    private int id;


    @Column(name = "openid", columnDefinition = "VARCHAR(64) COMMENT'投票人员'")
    private String openid;

    @Column(name = "start_time", columnDefinition = "DATETIME COMMENT'登陆时间'")
    private Date startTime;

    @Column(name = "end_time", columnDefinition = "DATETIME COMMENT'活动结束时间'")
    private Date endTime;

    @Column(name = "free_tickets", columnDefinition = "INT(11) COMMENT'免费票数'")
    private int free_tickets;

    @Column(name = "tickets", columnDefinition = "INT(4) COMMENT'充值票数'")
    private int tickets;

}
