package com.github.binarywang.demo.wx.miniapp.serviceImpl;

import com.github.binarywang.demo.wx.miniapp.config.BusinessException;
import com.github.binarywang.demo.wx.miniapp.controller.vm.UserVoteAddVm;
import com.github.binarywang.demo.wx.miniapp.dao.ActivityDao;
import com.github.binarywang.demo.wx.miniapp.dao.UserApplyDao;
import com.github.binarywang.demo.wx.miniapp.pojo.Activity;
import com.github.binarywang.demo.wx.miniapp.pojo.UserApply;
import com.github.binarywang.demo.wx.miniapp.service.UserApplyService;
import com.github.binarywang.demo.wx.miniapp.serviceImpl.dto.UserApplyDTO;
import com.github.binarywang.demo.wx.miniapp.utils.AliyunOSSUtil;
import com.github.binarywang.demo.wx.miniapp.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public void addUserVote(UserVoteAddVm vm) {

        if (userApplyDao.findByOpenId(vm.getOpenId()) != null) {
            throw new BusinessException("一个用户只能报名一次，不能再次报名");
        }
        if (userApplyDao.findByPhone(vm.getPhone()) != null) {
            throw new BusinessException("一个手机号只能报名一次，请更换手机号");
        }

        String[] vmPic = vm.getPic();
        String s=new String();
        if (vmPic.length == 0) {
            throw new BusinessException("请至少上传一张照片");
        } else {
            for (int i = 0; i < vmPic.length; i++) {
                if (i == vmPic.length - 1) {
                    s = s + vmPic[i];
                } else {

                    s=s+vmPic[i]+",";
                }
            }
        }

        UserApply userApply = UserApply.builder()
                .activityId(vm.getActivityId())
                .applyTime(new Date())
                .description(vm.getDescription())
                .gerder(vm.getGerder())
                .openId(vm.getOpenId())
                .name(vm.getName())
                .pic(s)
                .status("0")
                .phone(vm.getPhone())
                .totalVotes(0l)
                .build();
        userApplyDao.save(userApply);
    }

    @Override
    public List<UserApplyDTO> getOneVote(int id, String name) {
        List<UserApplyDTO> list = new ArrayList<>();
        if (name == null) {
            if (id == 0) {
                throw new BusinessException("报名id必传");
            }
            UserApply userApply = userApplyDao.findOne(id);
            if (userApply == null) {
                throw new BusinessException("无效的报名Id");
            }

            String pic = userApply.getPic();
            String[] pics = pic.split(",");
            UserApplyDTO userVoteDTO = new UserApplyDTO(userApply, pics);
            list.add(userVoteDTO);
        } else {
            List<UserApply> userApplies = userApplyDao.findByNameLike("%"+name+"%");
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
            appliesList(applies, list);
        }

        return list;
    }


    @Override
    public List<UserApplyDTO> findByReviewTime() {
        List<UserApplyDTO> list = new ArrayList<>();
        Sort sort = new Sort(Sort.Direction.DESC, "reviewTime");
        List<UserApply> applies = userApplyDao.findAll(sort);
        if (applies.size() > 0) {
            appliesList(applies, list);
        }

        return list;
    }

    @Override
    public Activity findActivity() {
        Activity activity = activityDao.findByStatus("0");

        return activity;
    }


    private List<UserApplyDTO> appliesList(List<UserApply> applies, List<UserApplyDTO> list) {
        for (UserApply apply : applies) {
            String[] pics = apply.getPic().split(",");
            UserApplyDTO userApplyDTO = new UserApplyDTO(apply, pics);
            list.add(userApplyDTO);
        }
        return list;
    }
}
