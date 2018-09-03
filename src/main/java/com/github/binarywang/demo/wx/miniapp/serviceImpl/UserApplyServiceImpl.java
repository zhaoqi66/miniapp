package com.github.binarywang.demo.wx.miniapp.serviceImpl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.github.binarywang.demo.wx.miniapp.config.BusinessException;
import com.github.binarywang.demo.wx.miniapp.controller.vm.UserApplyDetailVm;
import com.github.binarywang.demo.wx.miniapp.controller.vm.UserVoteAddVm;
import com.github.binarywang.demo.wx.miniapp.dao.ActivityDao;
import com.github.binarywang.demo.wx.miniapp.dao.UserApplyDao;
import com.github.binarywang.demo.wx.miniapp.domain.PhoneCode;
import com.github.binarywang.demo.wx.miniapp.pojo.Activity;
import com.github.binarywang.demo.wx.miniapp.pojo.UserApply;
import com.github.binarywang.demo.wx.miniapp.service.UserApplyService;
import com.github.binarywang.demo.wx.miniapp.serviceImpl.dto.UserApplyDTO;
import com.github.binarywang.demo.wx.miniapp.utils.AliyunOSSUtil;
import com.github.binarywang.demo.wx.miniapp.utils.RedisUtil;
import com.github.binarywang.demo.wx.miniapp.utils.SendSms;
import com.github.binarywang.demo.wx.miniapp.utils.SendSmsReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * UserVoteServiceImpl
 *
 * @author juan
 * @date 2018/8/29 16:11
 */
@Service
@Transactional
@Slf4j
public class UserApplyServiceImpl implements UserApplyService {

    @Autowired
    private UserApplyDao userApplyDao;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ActivityDao activityDao;

    @Override
    public void getPhoneCode(String phone) {
        if (phone == null) {
            throw new BusinessException("注册手机号码不能为空");
        }
        //创建验证码并在Redis中缓存
        PhoneCode phoneCode = redisUtil.createPhoneCode("register", phone, new BigDecimal("5").longValue());
        //发送手机验证码
        SendSmsReq req = new SendSmsReq(phone, SendSmsReq.SIGN_NAME, SendSmsReq.REGISTER_TEMPLATE_CODE,
                "{\"code\":\"" + phoneCode.getCode() + "\"}");

        try {
            SendSmsResponse response = SendSms.send(req);
            log.info("老师注册验证码短信接口返回的数据----------------");
            log.info("Code=" + response.getCode());
            log.info("Message=" + response.getMessage());
            log.info("RequestId=" + response.getRequestId());
            log.info("BizId=" + response.getBizId());
            if (!"OK".equals(response.getCode())) {
                //发送短信异常，删除对应的redis中的验证码
                redisUtil.remove(phoneCode.getPhoneCodeId());
                log.error("请联系管理员，老师注册短信发送调用短信接口异常,错误码Code：" +
                        response.getCode() + "；错误提示Message：" + response.getMessage());
                throw new IllegalArgumentException("请联系管理员，老师注册短信发送调用短信接口异常,错误码Code：" +
                        response.getCode() + "；错误提示Message：" + response.getMessage());
            }
        } catch (Exception e) {
            //发送短信异常，删除对应的redis中的验证码
            redisUtil.remove(phoneCode.getPhoneCodeId());
            log.error("发送短信注册验证码异常:" + e);
            throw new IllegalArgumentException("请联系管理员，老师注册短信验证码发送异常:" + e);
        }



    }

    @Override
    public void addUserVote(UserVoteAddVm vm) {

        if (userApplyDao.findByOpenId(vm.getOpenId()) != null) {
            throw new BusinessException("一个用户只能报名一次，不能再次报名");
        }
        if (userApplyDao.findByPhone(vm.getPhone()) != null) {
            throw new BusinessException("一个手机号只能报名一次，请更换手机号");
        }
        //验证手机验证码是否正确
        PhoneCode PhoneCode = (PhoneCode) redisUtil.get("register"+vm.getPhone());
        if (PhoneCode == null || !vm.getCode().equals(PhoneCode.getCode())) {
            throw new BusinessException("该手机号对应的短信验证码不存在：已失效或者验证码输入错误！");
        }
        String[] vmPic = vm.getPic();
        if (vmPic.length == 0) {
            throw new BusinessException("请至少上传一张照片");
        }

        UserApply userApply = UserApply.builder()
                .activityId(vm.getActivityId())
                .applyTime(new Date())
                .description(vm.getDescription())
                .gerder(vm.getGerder())
                .openId(vm.getOpenId())
                .name(vm.getName())
                .pic(vmPic.toString())
                .status("0")
                .totalVotes(0l)
                .build();
        userApplyDao.save(userApply);
    }

    @Override
    public List<UserApplyDTO> getOneVote(UserApplyDetailVm vm) {
        List<UserApplyDTO> list = new ArrayList<>();
        if (vm.getName() == null) {
            if (vm.getId() == 0) {
                throw new BusinessException("报名id必传");
            }
            UserApply userApply = userApplyDao.findOne(vm.getId());
            if (userApply == null) {
                throw new BusinessException("无效的报名Id");
            }

            String pic = userApply.getPic();
            String[] pics = pic.split(",");
            UserApplyDTO userVoteDTO = new UserApplyDTO(userApply, pics);
            list.add(userVoteDTO);
        } else {
            List<UserApply> userApplies = userApplyDao.findByNameIsLike(vm.getName());
            if (userApplies.size() > 0) {
                appliesList(userApplies, list);
            }
        }
        return list;
    }

    @Override
    public void deleteUserApply(int id) {
        if (id == 0) {
            throw new BusinessException("报名id必传");
        }
        UserApply userApply = userApplyDao.findOne(id);
        if (userApply == null) {
            throw new BusinessException("无效的报名Id");
        }
        //取消报名时把oss中的图片删除
        String[] pics = userApply.getPic().split(",");
        for (String pic : pics) {
            AliyunOSSUtil.deletePicture(pic);
        }

        userApplyDao.delete(id);

    }

    @Override
    public List<UserApplyDTO> findAllOrRank() {
        List<UserApplyDTO> list = new ArrayList<>();
        Sort sort = new Sort(Sort.Direction.DESC, "totalVotes");
        List<UserApply> applies = userApplyDao.findAll(sort);
        if (applies.size() > 0) {
            appliesList(applies,list);
        }

        return list;
    }


    @Override
    public List<UserApplyDTO> findByReviewTime() {
        List<UserApplyDTO> list = new ArrayList<>();
        Sort sort = new Sort(Sort.Direction.DESC, "reviewTime");
        List<UserApply> applies = userApplyDao.findAll(sort);
        if (applies.size() > 0) {
            appliesList(applies,list);
        }

        return list;
    }

    @Override
    public Activity findActivity() {
        Activity activity = activityDao.findByStatus("0");

        return activity;
    }


    private List<UserApplyDTO> appliesList(List<UserApply> applies,List<UserApplyDTO> list) {
        for (UserApply apply : applies) {
            String[] pics = apply.getPic().split(",");
            UserApplyDTO userApplyDTO = new UserApplyDTO(apply, pics);
            list.add(userApplyDTO);
        }
        return list;
    }
}
