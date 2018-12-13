package com.github.binarywang.demo.wx.miniapp.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "goods_order_shipping")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsOrderShipping {
    /**
     * 物流id
     */
    @Id
    @Column(name = "shipping_id")
    private String shippingId;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 收货人名字
     */
    @Column(name = "shipping_customer_name")
    private String shippingCustomerName;

    /**
     * 收货人联系方式
     */
    @Column(name = "shipping_customer_telephone")
    private String shippingCustomerTelephone;

    /**
     * 收货详细地址
     */
    @Column(name = "shipping_area_detail")
    private String shippingAreaDetail;

    /**
     * 物流单创建时间
     */
    @Column(name = "shipping_create_time")
    private Date shippingCreateTime;

    /**
     * 物流单更新时间
     */
    @Column(name = "shipping_update_time")
    private Date shippingUpdateTime;

    /**
     * 物流方式：0、物流配送，1、自提
     */
    @Column(name = "shipping_type")
    private String shippingType;

    /**
     * 物流名称
     */
    @Column(name = "shipping_name")
    private String shippingName;

    /**
     * 物流单号
     */
    @Column(name = "shipping_code")
    private String shippingCode;
}