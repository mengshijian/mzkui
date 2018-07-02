package com.msj.mzkui.websocket;

import com.cootf.log4droid.websocket.entity.ServerMessage;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

//@Component
public class TimeSender {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public TimeSender(final SimpMessagingTemplate broker) {
        this.messagingTemplate = broker;
    }

    @Scheduled(fixedRate = 5000)
    public void run() {
        String time = LocalTime.now().format(TIME_FORMAT);

        logger.info("Time broadcast: {}", time);
        messagingTemplate.convertAndSend("/topic/subscribeTest", new ServerMessage("用户下线"));
    }
}