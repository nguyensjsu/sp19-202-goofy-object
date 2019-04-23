package com.goofyobject.tetris.contoller;

import java.util.Date;

import com.goofyobject.tetris.service.GameRoomService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
public class EventController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GameRoomService gameRoomService;

    @EventListener(SessionConnectEvent.class)
    public void handleWebsocketConnectListner(SessionConnectEvent event) {

        Date date = new Date();

        logger.info("Received a new web socket connection : " + date.getTime());
    }

    @EventListener(SessionDisconnectEvent.class)
    public void handleWebsocketDisconnectListner(SessionDisconnectEvent event) {

        Date date = new Date();
        
        logger.info("Received a new web socket connection : " + date.getTime());
    }

}