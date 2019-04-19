package com.goofyobject.tetris.contoller;

import com.goofyobject.tetris.domain.GameEngine;
import com.goofyobject.tetris.domain.Position;
import com.goofyobject.tetris.service.GameRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
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

    @MessageMapping("/addToQueue")
    public void addUser(SimpMessageHeaderAccessor headerAccessor) throws Exception {
        String sessionId = headerAccessor.getSessionId();
        boolean isAdded = gameRoomService.addPlayerToQueue(sessionId);
        matchOpponent(sessionId);
        messagingTemplate.convertAndSend("/topic/add?" + sessionId,isAdded);
        
    }

    public void matchOpponent(String sessionId) throws Exception {

        int i = 0;
        while (i < 500) {

            gameRoomService.findOpponent(sessionId);

            GameEngine gameEngine = gameRoomService.getEngine(sessionId);

            if (gameEngine != null) {

                messagingTemplate.convertAndSend("/topic/join","");

                String playerOneSessionID = gameEngine.getPlayer1();

                if (sessionId.equals(playerOneSessionID)) {
                    messagingTemplate.convertAndSend("/topic/update?" + sessionId, "");
                }
                return;
            }
            Thread.sleep(500);
            i++;
        }
        gameRoomService.removePlayerFromQueue(sessionId);
        messagingTemplate.convertAndSend("/topic/retry?" + sessionId,"");
    }

    @MessageMapping("/putPiece")
    public void putPiece(SimpMessageHeaderAccessor headerAccessor,Position position) throws Exception {

        String sessionId = headerAccessor.getSessionId();

        GameEngine gameEngine = gameRoomService.getEngine(sessionId);

        //if valid move
        if (gameEngine != null && gameEngine.putPiece(sessionId, position)){

            String winnerSessionId = gameEngine.checkWinner(position);
            
            if (winnerSessionId != null){
                messagingTemplate.convertAndSend("/topic/win", winnerSessionId);
            }

            if (gameEngine.checkDraw()){
                messagingTemplate.convertAndSend("/topic/win", "");
            }

            String readPlayerSessionId = gameEngine.readyPlayer();
            messagingTemplate.convertAndSend("/topic/update?" + readPlayerSessionId, position);
            
        }
        
        

    }

}