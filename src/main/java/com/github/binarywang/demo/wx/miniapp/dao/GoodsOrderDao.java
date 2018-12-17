package com.github.binarywang.demo.wx.miniapp.dao;

import com.github.binarywang.demo.wx.miniapp.pojo.GoodsOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author FHZD.xiaoxun
 * @date 2018/12/10
 */
@Repository
public interface GoodsOrderDao extends JpaRepository<GoodsOrder,String>,JpaSpecificationExecutor<GoodsOrder> {
    GoodsOrder findByOpenId(@Param("openId")String openId);
}
