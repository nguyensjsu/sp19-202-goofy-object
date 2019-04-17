package com.goofyobject.tetris.contoller;

import com.goofyobject.tetris.domain.Brick;
import com.goofyobject.tetris.domain.GameEngine;
import com.goofyobject.tetris.domain.Player;
import com.goofyobject.tetris.service.GameRoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    @Autowired
    GameRoomService gameRoomService;
    // @MessageMapping("/moveDown")
    // @SendTo("/topic/move")
    // public Brick move(SimpMessageHeaderAccessor headerAccessor,Brick brick)
    // throws Exception {
    // // WebSocketSession session,
    // System.out.println(headerAccessor.getSessionId());
    // brick.moveDown();
    // return brick;
    // }

    @MessageMapping("/addUser")
    @SendTo("/topic/add")
    public Player addUser(SimpMessageHeaderAccessor headerAccessor, Player player) throws Exception {
        player.setSessionId(headerAccessor.getSessionId());
        if (gameRoomService.addPlayer(player)) {
            return player;
        }
        return null;
    }

    @MessageMapping("/create")
    @SendTo("/topic/create")
    public void createRoom(SimpMessageHeaderAccessor headerAccessor, Player player) throws Exception {
        gameRoomService.createRoom(player);
    }

    @MessageMapping("/join")
    @SendTo("/topic/join")
    public void matchOpponent(SimpMessageHeaderAccessor headerAccessor, Player player) throws Exception {

        GameEngine e = gameRoomService.findRoom(player);

        if (e == null) {
            return;
        }

        //todo: send message to p1 and p2

    }

}