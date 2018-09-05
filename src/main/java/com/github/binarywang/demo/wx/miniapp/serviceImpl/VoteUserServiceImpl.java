package com.github.binarywang.demo.wx.miniapp.serviceImpl;

import com.github.binarywang.demo.wx.miniapp.dao.VoteUserDao;
import com.github.binarywang.demo.wx.miniapp.pojo.VoteUser;
import com.github.binarywang.demo.wx.miniapp.service.VoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class VoteUserServiceImpl implements VoteUserService {


    @Autowired
    private VoteUserDao voteUserDao;


    @Override
    public void save(VoteUser user) {
        voteUserDao.save(user);
    }

    @Override
    public int findOneByOpenid(String openid) {
        return voteUserDao.findOneByOpenid(openid);
    }

    @Override
    public void deleteFreeTicketTask() {
        //查询免费票数大于1的投票用户
//        voteUserDao.findByFreeTickets()
    }

}
