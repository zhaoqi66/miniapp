package com.github.binarywang.demo.wx.miniapp.dao;

import com.github.binarywang.demo.wx.miniapp.pojo.VoteOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

/**
 * VoteOrderDao
 *
 * @author juan
 * @date 2018/9/10 10:36
 */
public interface VoteOrderDao extends JpaRepository<VoteOrder,Integer>,JpaSpecificationExecutor<VoteOrder> {

    VoteOrder findByOpenId(@Param("openId")String openId);
}
