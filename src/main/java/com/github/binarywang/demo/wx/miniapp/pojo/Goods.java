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

@Table(name = "goods")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Goods {
    /**
     * 商品ID
     */
    @Id
    @Column(name = "goods_id")
    private String goodsId;

    /**
     * 商品展示ID
     */
    @Column(name = "goods_show_id")
    private String goodsShowId;

    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    private String goodsName;

    /**
     * 商品图片
     */
    @Column(name = "goods_img")
    private String goodsImg;

    /**
     * 商品价格
     */
    @Column(name = "goods_price")
    private BigDecimal goodsPrice;

    /**
     * 商品数量
     */
    @Column(name = "goods_num")
    private Integer goodsNum;

    /**
     * 商品排序
     */
    @Column(name = "goods_sort")
    private Integer goodsSort;

    /**
     * 创建时间
     */
    @Column(name = "goods_create_time")
    private Date goodsCreateTime;

    /**
     * 更新时间
     */
    @Column(name = "goods_update_time")
    private Date goodsUpdateTime;

    /**
     * 商品删除标记(0删除 1正常)
     */
    @Column(name = "goods_delflag")
    private Byte goodsDelflag;

    /**
     * 商品删除时间
     */
    @Column(name = "goods_deltime")
    private Date goodsDeltime;

    /**
     * 商品描述
     */
    @Column(name = "goods_brief")
    private String goodsBrief;

    /**
     * 分类ID
     */
    @Column(name = "type_id")
    private String typeId;

    /**
     * 品牌ID
     */
    @Column(name = "brand_id")
    private String brandId;

    /**
     * 上下架状态(0下架 1上架)
     */
    @Column(name = "goods_status")
    private Byte goodsStatus;

    /**
     * 上架时间
     */
    @Column(name = "goods_added_time")
    private Date goodsAddedTime;

    /**
     * 下架时间
     */
    @Column(name = "goods_unadded_time")
    private Date goodsUnaddedTime;

}