package com.github.binarywang.demo.wx.miniapp.serviceImpl.dto;

import com.github.binarywang.demo.wx.miniapp.pojo.Goods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author FHZD.xiaoxun
 * @date 2018/12/11
 */
@Data
@ApiModel(value = "推荐商品返回参数")
public class GoodsCommendDTO {
    @ApiModelProperty(value = "商品表主键")
    private String goodsId;

    @ApiModelProperty(value = "商品展示ID")
    private String goodsShowId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品图片")
    private String[] goodsImg;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal goodsPrice;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNum;

    @ApiModelProperty(value = "商品描述")
    private String goodsBrief;

    public GoodsCommendDTO() {
    }

    public GoodsCommendDTO(Goods goods , String[] imgUrl) {
        this.goodsId = goods.getGoodsId();
        this.goodsShowId = goods.getGoodsShowId();
        this.goodsName = goods.getGoodsName();
        this.goodsImg = imgUrl;
        this.goodsPrice = goods.getGoodsPrice();
        this.goodsNum = goods.getGoodsNum();
    }
}
