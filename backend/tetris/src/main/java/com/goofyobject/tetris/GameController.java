package com.goofyobject.tetris;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController{

   
    @MessageMapping("/moveDown")
    @SendTo("/topic/move")
    public Brick move(Brick brick) throws Exception {
        brick.moveDown();
        return brick;
    }

}