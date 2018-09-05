package com.github.binarywang.demo.wx.miniapp.utils;

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
    //每天凌晨执行一次
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteFreeTicketTask() {
        
    }
}
