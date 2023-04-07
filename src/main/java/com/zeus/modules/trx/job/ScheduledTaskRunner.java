package com.zeus.modules.trx.job;

import com.zeus.modules.trx.service.ScheduledTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduledTaskRunner implements ApplicationRunner {

    private Logger log = Logger.getLogger(ScheduledTaskRunner.class);

    @Autowired
    private ScheduledTaskService scheduledTaskService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("----初始化定时任务开始----");
        scheduledTaskService.All_Bot();
        log.info("----初始化定时任务完成----");
    }
}