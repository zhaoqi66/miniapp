package com.github.binarywang.demo.wx.miniapp.controller;

import com.github.binarywang.demo.wx.miniapp.config.ResponseModel;
import com.github.binarywang.demo.wx.miniapp.config.ResponseModels;
import com.github.binarywang.demo.wx.miniapp.controller.vm.UserApplyDetailVm;
import com.github.binarywang.demo.wx.miniapp.controller.vm.UserVoteAddVm;
import com.github.binarywang.demo.wx.miniapp.pojo.Activity;
import com.github.binarywang.demo.wx.miniapp.service.UserApplyService;
import com.github.binarywang.demo.wx.miniapp.serviceImpl.dto.UserApplyDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * UserVoteController
 * api:我要报名接口  /miniApi/apply/add post
 * api:报名详情接口 和 投票首页 搜索框中 请输入姓名和编号接口 /miniApi/apply/findUserApply get
 * api:取消报名接口   /miniApi/apply/cancel delete
 * api:全部参赛和排名接口 /miniApi/apply/findAllOrRank get
 * api:最新参赛接口 /miniApi/apply/findByReviewTime get
 * api:活动规则接口 /miniApi/apply/findActivity get
 * @author juan
 * @date 2018/8/29 15:56
 */
@RestController
@RequestMapping("miniApi")
@Api(description = "选手参加报名模块")
public class UserApplyController {
    private static final Logger logger = LoggerFactory.getLogger(UserApplyController.class);

    @Autowired
    private UserApplyService userApplyService;


    @PutMapping("/apply/add")
    @ApiOperation(value = "我要报名接口")
    public ResponseModel addUserVote(@RequestBody @Valid UserVoteAddVm vm) {
        logger.info("我要报名接口 UserVoteAddVm={}",vm);
        userApplyService.addUserVote(vm);
        return ResponseModels.ok();
    }

    @GetMapping("/apply/findUserApply")
    @ApiOperation(value = "报名详情接口 和 投票首页 搜索框中 请输入姓名和编号接口")
    public ResponseModel<List<UserApplyDTO>> getOneVote(UserApplyDetailVm vm) {
        logger.info("报名详情接口 和 投票首页 搜索框中 请输入姓名和编号接口 UserApplyDetailVm={}",vm);
        List<UserApplyDTO> userApplyDTOS = userApplyService.getOneVote(vm);
        return ResponseModels.ok(userApplyDTOS);
    }

    @DeleteMapping("/apply/cancel")
    @ApiOperation(value = "取消报名接口")
    public ResponseModel deleteUserApply(int id) {
        logger.info("取消报名接口 id={}",id);
        userApplyService.deleteUserApply(id);
        return ResponseModels.ok();
    }

    @GetMapping("/apply/findAllOrRank")
    @ApiOperation(value = "全部参赛和排名接口")
    public ResponseModel<List<UserApplyDTO>> findAllOrRank() {
        logger.info("全部参赛和排名接口");
        List<UserApplyDTO> userApplyDTOS = userApplyService.findAllOrRank();
        return ResponseModels.ok(userApplyDTOS);
    }

    @GetMapping("/apply/findByReviewTime")
    @ApiOperation(value = "最新参赛接口")
    public ResponseModel<List<UserApplyDTO>> findByReviewTime() {
        logger.info("最新参赛接口");
        List<UserApplyDTO> userApplyDTOS = userApplyService.findByReviewTime();
        return ResponseModels.ok(userApplyDTOS);
    }

    @GetMapping("/apply/findActivity ")
    @ApiOperation(value = "活动规则接口")
    public ResponseModel findActivity() {
        Activity activity = userApplyService.findActivity();
        return ResponseModels.ok(activity);
    }


}
