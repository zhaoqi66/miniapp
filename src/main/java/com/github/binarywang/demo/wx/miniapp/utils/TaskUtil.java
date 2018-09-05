package com.github.binarywang.demo.wx.miniapp.utils;

import com.github.binarywang.demo.wx.miniapp.service.VoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * TaskUtil
 *
 * @author juan
 * @date 2018/9/5 10:31
 */
@Component
public class TaskUtil {

    @Autowired
    private VoteUserService voteUserService;
    //每天凌晨执行一次
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteFreeTicketTask() {
        voteUserService.deleteFreeTicketTask();
        
    }
}
