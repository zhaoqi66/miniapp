package com.github.binarywang.demo.wx.miniapp.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "goods_order")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsOrder {
    /**
     * 订单id
     */
    @Id
    @Column(name = "order_id")
    private String orderId;

    /**
     * 订单展示ID
     */
    @Column(name = "order_show_id")
    private String orderShowId;

    /**
     * 商品总金额
     */
    @Column(name = "order_old_price")
    private BigDecimal orderOldPrice;

    /**
     * 订单总金额
     */
    @Column(name = "order_pre_price")
    private BigDecimal orderPrePrice;

    /**
     * 实付金额
     */
    @Column(name = "order_price")
    private BigDecimal orderPrice;

    /**
     * 商品总数量
     */
    @Column(name = "order_number")
    private Integer orderNumber;

    /**
     * 支付类型：1、在线支付，2、货到付款
     */
    @Column(name = "order_line_pay")
    private String orderLinePay;

    /**
     * 邮费
     */
    @Column(name = "express_price")
    private BigDecimal expressPrice;

    /**
     * 状态：1、未付款，2、已付款，未发货，3、未取货，4、已发货，5、交易成功，6、交易取消
     */
    @Column(name = "order_status")
    private String orderStatus;

    /**
     * 订单创建时间
     */
    @Column(name = "order_create_time")
    private Date orderCreateTime;

    /**
     * 订单更新时间
     */
    @Column(name = "order_update_time")
    private Date orderUpdateTime;

    /**
     * 付款时间
     */
    @Column(name = "order_pay_time")
    private Date orderPayTime;

    /**
     * 发货时间
     */
    @Column(name = "send_express")
    private Date sendExpress;

    /**
     * 交易完成时间
     */
    @Column(name = "get_goods_time")
    private Date getGoodsTime;

    /**
     * 交易关闭时间
     */
    @Column(name = "order_cancel_time")
    private Date orderCancelTime;

    /**
     * 物流ID
     */
    @Column(name = "shipping_id")
    private String shippingId;

    /**
     * 用户ID
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 买家留言
     */
    @Column(name = "order_buyer_message")
    private String orderBuyerMessage;

    /**
     * 用户预支付id
     */
    @Column(name = "prepay_id")
    private String prepayId;

    /**
     * 订单描述
     */
    @Column(name = "order_desc")
    private String orderDesc;

    /**
     * 微信支付成功的交易单号
     */
    @Column(name = "transaction_id")
    private String transactionId;
}