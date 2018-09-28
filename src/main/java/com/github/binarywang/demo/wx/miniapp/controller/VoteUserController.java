package com.github.binarywang.demo.wx.miniapp.controller;

import com.github.binarywang.demo.wx.miniapp.config.ResponseModel;
import com.github.binarywang.demo.wx.miniapp.config.ResponseModels;
import com.github.binarywang.demo.wx.miniapp.controller.vm.ClickVoteVm;
import com.github.binarywang.demo.wx.miniapp.controller.vm.VoteOrderAddVm;
import com.github.binarywang.demo.wx.miniapp.service.VoteUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

/**
 * VoteUserController
 * api:1人气和展示input框中的总票数是同一个接口 miniApi/vote/freeVote get
 * api:投人气按钮接口 miniApi/vote/clickVote post
 * api:投人气订单生成接口 miniApi/vote/voteOrder post
 * api:充值人气接口接口 miniApi/vote/payVoteOrder post
 * api:支付完成回调接口 miniApi/vote/weiXinNotify post
 *
 * @author juan
 * @date 2018/9/6 14:38
 */
@RestController
@RequestMapping("miniApi")
@Api(description = "选手投票模块")
public class VoteUserController {
    private static final Logger logger = LoggerFactory.getLogger(VoteUserController.class);

    @Autowired
    private VoteUserService voteUserService;

    @GetMapping("/vote/freeVote")
    @ApiOperation(value = "1人气和展示input框中的总票数是同一个接口")
    public ResponseModel<Map> getFreeVote(@RequestParam(name = "openId", required = true) String openId) {
        logger.info("1人气和展示input框中的总票数是同一个接口 openId={}", openId);
        Map vote = voteUserService.getFreeVote(openId);
        return ResponseModels.ok(vote);
    }

    @PostMapping("/vote/clickVote")
    @ApiOperation("投人气按钮接口")
    public ResponseModel clickVote(@RequestBody ClickVoteVm vm) {
        logger.info("投人气按钮接口 ClickVoteVm={}", vm);
        voteUserService.clickVote(vm);
        return ResponseModels.ok();
    }

    @PutMapping("/vote/voteOrder")
    @ApiOperation(value = "投人气订单生成接口")
    public ResponseModel<Integer> createVoteOrder(@Valid @RequestBody VoteOrderAddVm vm) {
        logger.info("投人气订单生成接口 VoteOrderAddVm={}", vm);
        int orderId = voteUserService.createVoteOrder(vm);
        return ResponseModels.ok(orderId);

    }

    @PostMapping("/vote/payVoteOrder")
    @ApiOperation(value = "充值人气接口接口")
    public ResponseModel payVoteOrder(@RequestParam(name = "orderId", required = true) int orderId, HttpServletRequest request) {
        logger.info("充值人气接口接口 orderId={}", orderId);
        Map map = voteUserService.payVoteOrder(orderId, request);
        return ResponseModels.ok(map);

    }

    @PostMapping("/vote/weiXinNotify")
    @ApiOperation(value = "支付完成回调接口")
    public ResponseModel WXPayBack(HttpServletRequest request, HttpServletResponse response) throws IOException, JDOMException {
        logger.info("支付完成回调接口 ");
        voteUserService.getWXPayBack(response, request);
        return ResponseModels.ok();

    }


}
