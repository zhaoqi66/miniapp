package com.github.binarywang.demo.wx.miniapp.service;

import com.github.binarywang.demo.wx.miniapp.controller.vm.UserApplyDetailVm;
import com.github.binarywang.demo.wx.miniapp.controller.vm.UserVoteAddVm;
import com.github.binarywang.demo.wx.miniapp.pojo.Activity;
import com.github.binarywang.demo.wx.miniapp.serviceImpl.dto.UserApplyDTO;

import java.util.List;

/**
 * UserApplyService
 *
 * @author LT
 * @date 2018/8/29 16:11
 */
public interface UserApplyService {

    void getPhoneCode(String phone);

    void addUserVote(UserVoteAddVm vm);

    List<UserApplyDTO> getOneVote(UserApplyDetailVm vm);

    void deleteUserApply(int id);

    List<UserApplyDTO> findAllOrRank();

    List<UserApplyDTO> findByReviewTime();

    Activity findActivity();
}
