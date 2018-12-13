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

@Table(name = "goods_order_product")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsOrderProduct {
    /**
     * 订单详情ID
     */
    @Id
    @Column(name = "order_product_id")
    private String orderProductId;

    /**
     * 商品id
     */
    @Column(name = "goods_id")
    private String goodsId;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 商品购买数量
     */
    @Column(name = "order_goods_number")
    private Integer orderGoodsNumber;

    /**
     * 商品名称
     */
    @Column(name = "order_goods_title")
    private String orderGoodsTitle;

    /**
     * 商品单价
     */
    @Column(name = "order_goods_price")
    private BigDecimal orderGoodsPrice;

    /**
     * 商品总金额
     */
    @Column(name = "order_total_fee")
    private BigDecimal orderTotalFee;

    /**
     * 商品图片
     */
    @Column(name = "order_goods_image")
    private String orderGoodsImage;

}