package com.github.binarywang.demo.wx.miniapp.dao;

import com.github.binarywang.demo.wx.miniapp.pojo.VoteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoteUserDao extends JpaSpecificationExecutor<VoteUser>,JpaRepository<VoteUser,Integer> {

    int findOneByOpenid(String openid);

    List<VoteUser> findByFreeticketsGreaterThan(@Param("freeTickets")int freeTickets);

    VoteUser findByOpenid(@Param("openId")String openId);
}
