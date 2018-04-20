package com.ctf.log.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


//4.设定的定时类

@Component
public class Scheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "0 0/1 * * * ?") // 每分钟执行一次
    public void Timer() {
        logger.info("Scheduler开始……");
    }
}