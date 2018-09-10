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
 * VoteOrder 投票订单表
 *
 * @author juan
 * @date 2018/9/7 14:31
 */
@Entity
@Table(name = "vote_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "order_id", columnDefinition = "VARCHAR(64) COMMENT'管理端订单号展示id'")
    private String orderId;

    @Column(name = "prepay_id", columnDefinition = "VARCHAR(64) COMMENT'预支付id'")
    private String prepayId;

    @Column(name = "transaction_id", columnDefinition = "VARCHAR(64) COMMENT'微信支付订单号'")
    private String transactionId;

    @Column(name = "order_money", columnDefinition = "DECIMAL(8,2) COMMENT'订单金额'")
    private double orderMoney;

    @Column(name = "vote_poll", columnDefinition = "INT(8) COMMENT'人气数'")
    private int votePoll;

    @Column(name = "create_time", columnDefinition = "DATETIME COMMENT'订单创建时间'")
    private Date createTime;

    @Column(name = "open_id", columnDefinition = "VARCHAR(64) COMMENT'注册用户id'")
    private String openId;

    @Column(name = "order_desc", columnDefinition = "VARCHAR(64) COMMENT'订单描述'")
    private String orderDesc;

}
