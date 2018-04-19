package com.ctf.log.server.component;

import com.ctf.log.server.utils.LogWrapper;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Charles
 * @create 2017/7/21 14:55
 */
@Component
@PropertySource(value = "classpath:/rocketmq.properties")
public class RocketMQMessageSender {
    private static final LogWrapper logger = LogWrapper.getLogger(RocketMQMessageSender.class);

}
