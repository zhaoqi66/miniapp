package com.github.binarywang.demo.wx.miniapp.dao;

import com.github.binarywang.demo.wx.miniapp.pojo.UserApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserVoteDao
 *
 * @author juan
 * @date 2018/8/29 16:09
 */
@Repository
public interface UserApplyDao extends JpaSpecificationExecutor<UserApply>,JpaRepository<UserApply,Integer> {

    UserApply findByOpenId(@Param("openId")String openId);

    UserApply findByPhone(@Param("phone") String phone);

    List<UserApply> findByNameIsLike(@Param("name") String name);

}
