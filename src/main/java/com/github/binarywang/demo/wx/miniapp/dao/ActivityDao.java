package com.github.binarywang.demo.wx.miniapp.dao;

import com.github.binarywang.demo.wx.miniapp.pojo.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * ActivityDao
 *
 * @author juan
 * @date 2018/8/30 17:33
 */
@Repository
public interface ActivityDao extends JpaRepository<Activity,Integer>,JpaSpecificationExecutor<Activity> {
    Activity findByStatus(@Param("status")String status);
}
