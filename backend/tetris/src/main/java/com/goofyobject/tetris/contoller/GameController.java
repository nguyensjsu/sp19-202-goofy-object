package com.goofyobject.tetris.contoller;

import com.goofyobject.tetris.domain.Game;
import com.goofyobject.tetris.domain.Player;
import com.goofyobject.tetris.service.GameRoomService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    @Autowired
    GameRoomService gameRoomService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @MessageMapping("/addUser")
    @SendTo("/topic/add")
    public Player addUser(SimpMessageHeaderAccessor headerAccessor, Player player) throws Exception {
        player.setSessionId(headerAccessor.getSessionId());
        if (gameRoomService.addPlayerToQueue(player)) {
            headerAccessor.getSessionAttributes().put("username",player.getName());
            return player;
        }
        return null;
    }

    @MessageMapping("/match")
    public void matchOpponent(SimpMessageHeaderAccessor headerAccessor, Player player) throws Exception {

        player.setSessionId(headerAccessor.getSessionId());
        gameRoomService.findRoom(player);

        Game game = gameRoomService.getGameBoard(player);

        if (game != null){
            messagingTemplate.convertAndSend("/topic/join"+player.getName(), game);

        }else{
            Thread.sleep(600);
            messagingTemplate.convertAndSend("/topic/retry"+player.getName(), player);
        }

    }

}