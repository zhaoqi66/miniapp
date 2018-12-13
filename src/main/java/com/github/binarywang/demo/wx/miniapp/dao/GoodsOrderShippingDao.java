package com.github.binarywang.demo.wx.miniapp.dao;

import com.github.binarywang.demo.wx.miniapp.pojo.GoodsOrderShipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author FHZD.xiaoxun
 * @date 2018/12/12
 */
@Repository
public interface GoodsOrderShippingDao extends JpaRepository<GoodsOrderShipping,String>,JpaSpecificationExecutor<GoodsOrderShipping> {
}
