package com.goofyobject.tetris.contoller;


import com.goofyobject.tetris.domain.Brick;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class GameController{

   
    @MessageMapping("/moveDown")
    @SendTo("/topic/move")
    public Brick move(SimpMessageHeaderAccessor headerAccessor,Brick brick) throws Exception {
       // WebSocketSession session, 
        System.out.println(headerAccessor.getSessionId());
        brick.moveDown();
        return brick;
    }

}