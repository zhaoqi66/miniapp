package com.github.binarywang.demo.wx.miniapp.dao;

import com.github.binarywang.demo.wx.miniapp.pojo.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author FHZD.xiaoxun
 * @date 2018/12/10
 */
public interface GoodsDao extends JpaRepository<Goods,String>,JpaSpecificationExecutor<Goods> {
}
