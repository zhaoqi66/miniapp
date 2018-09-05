package com.github.binarywang.demo.wx.miniapp.dao;

import com.github.binarywang.demo.wx.miniapp.pojo.VoteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VoteUserDao extends JpaSpecificationExecutor<VoteUser>,JpaRepository<VoteUser,Integer> {

    int findOneByOpenid(String openid);

//    List<VoteUser> findByFreeTickets(@Param("freeTickets")int freeTickets);
}
