package com.goofyobject.tetris.controller;

import java.util.Date;
import java.util.HashMap;

import com.goofyobject.tetris.domain.Code;
import com.goofyobject.tetris.domain.ConcreteMessage;
import com.goofyobject.tetris.domain.Reply;
import com.goofyobject.tetris.domain.Status;
import com.goofyobject.tetris.domain.User;
import com.goofyobject.tetris.service.GameRoomService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
public class EventController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GameRoomService gameRoomService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    public void sendReply(String topicName, User user, Reply[] reply){
        Reply cur = reply[0];
        for (int i = 1; i < reply.length; i++){
            cur.setDecorator(reply[i]);
            cur = reply[i];
        }
        cur.setDecorator(new ConcreteMessage());
        HashMap<String,Object> res = new HashMap<>();
        reply[0].getObj(res);

        messagingTemplate.convertAndSend("/topic/" + topicName + "?" + user.getUsername(), res);
    }

    @EventListener(SessionConnectEvent.class)
    public void handleWebsocketConnectListner(SessionConnectEvent event) {
        Date date = new Date();
        logger.info("Received a new web socket connection : " + date.getTime());
    }

    @EventListener(SessionDisconnectEvent.class)
    public void handleWebsocketDisconnectListner(SessionDisconnectEvent event) {
        Date date = new Date();
        logger.info("Disconnet a web socket connection : " + date.getTime());


        User informPlayer = gameRoomService.PlayerLeave(event.getSessionId());

        if (informPlayer != null){
            sendReply("update",informPlayer,new Reply[]{new Status(Code.LEAVE)});
        }
        

    }

}