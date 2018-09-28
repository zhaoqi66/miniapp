package com.github.binarywang.demo.wx.miniapp.service;

import com.github.binarywang.demo.wx.miniapp.controller.vm.UserVoteAddVm;
import com.github.binarywang.demo.wx.miniapp.pojo.Activity;
import com.github.binarywang.demo.wx.miniapp.serviceImpl.dto.ActivityDTO;
import com.github.binarywang.demo.wx.miniapp.serviceImpl.dto.UserApplyDTO;

import java.util.List;
import java.util.Map;

/**
 * UserApplyService
 *
 * @author LT
 * @date 2018/8/29 16:11
 */
public interface UserApplyService {

    void addUserVote(UserVoteAddVm vm);

    List<UserApplyDTO> getOneVote(Integer id, String name,String openId);

    void deleteUserApply(Integer id);

    List<UserApplyDTO> findAllOrRank();

    List<UserApplyDTO> findByReviewTime();

    ActivityDTO findActivity();

    Map findTotal(int activityId);

    String isApply(String openId);
}
