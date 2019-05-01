package com.goofyobject.tetris.contoller;

import java.util.HashMap;

import com.goofyobject.tetris.domain.Code;
import com.goofyobject.tetris.domain.ConcreteMessage;
import com.goofyobject.tetris.domain.Move;
import com.goofyobject.tetris.domain.Status;
import com.goofyobject.tetris.domain.User;
import com.goofyobject.tetris.domain.Reply;


import com.goofyobject.tetris.service.GameRoomService;
import com.goofyobject.tetris.game.entity.Position;
import com.goofyobject.tetris.game.entity.GameLogic;

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
    public void addUser(SimpMessageHeaderAccessor headerAccessor, User user) throws Exception {
        String username = user.getUsername();
        logger.info("-----------username:" + username);
        String sessionId = headerAccessor.getSessionId();
        boolean isAdded = gameRoomService.addPlayerToQueue(username, sessionId);

        if (!isAdded) {
            sendReply("added",user, new Reply[]{new Status(Code.FAIL)});
            return;
        }
        sendReply("added",user,new Reply[]{new Status(Code.OK)});

        matchOpponent(user,sessionId);
    }

    public void sendReply(String topicName, User user, Reply[] reply){

        Reply cur = user;
        
        for (int i = 0; i < reply.length; i++){
            cur.setDecorator(reply[i]);
            cur = reply[i];
        }

        HashMap<String,Object> res = new HashMap<>();
        user.getObj(res);

        messagingTemplate.convertAndSend("/topic/" + topicName + "?" + user.getUsername(), res);
    }

    public void sendResult(HashMap<String, Integer> hm, Move move){
        for (String username : hm.keySet()) {
            Status status = new Status(hm.get(username));
            sendReply("update",new User(username), new Reply[] {move,status});
        }
    }

    public void matchOpponent(User user, String sessionId) throws Exception {

        String username = user.getUsername();
        int i = 0;
        while (i < 200) {
            gameRoomService.findOpponent(username);
            GameLogic gameLogic = gameRoomService.getEngine(username);

            if (gameLogic != null) {
                String p1 = gameLogic.getId1();
                String p2 = gameLogic.getId2();
                
                String oppnentName = p1;

                Status color = new Status(Code.WHITE);

                if (username.equals(p1)){
                    oppnentName = p2;
                    color = new Status(Code.BLACK);
                }

                sendReply("join",user, new Reply[]{new User(oppnentName), color});
                return;
            }
            Thread.sleep(500);
            i++;
        }

        gameRoomService.removePlayerFromQueue(username, sessionId);
        sendReply("join",user, new Reply[]{new Status(Code.FAIL)});
    }

    @MessageMapping("/putPiece")
    public void putPiece(Move move) throws Exception {
        String username = move.getUsername();
        GameLogic gameLogic = gameRoomService.getEngine(username);
        Position pos = new Position(move.getX(),move.getY());
        if (gameLogic != null && gameLogic.putPiece(username, pos)) {
            HashMap<String,Integer> hm =  new HashMap<>();
            String p1 = gameLogic.getId1();
            String p2 = gameLogic.getId2();
            String winner = gameLogic.checkWinner(pos);

            if (winner != null) {

                String loser = p1;
                if (winner.equals(p1)){
                    loser = p2;
                }

                hm.put(winner, Code.WIN);
                hm.put(loser, Code.LOSE);
                sendResult(hm,move);
                gameRoomService.removePlayersFromGame(p1,p2);
            }else if (gameLogic.checkDraw()) {
                hm.put(p1,Code.DRAW);
                hm.put(p2,Code.DRAW);
                sendResult(hm,move);
                gameRoomService.removePlayersFromGame(p1,p2);
            }else{
                User readyPlayer = new User(gameLogic.readyPlayer());
                logger.info(readyPlayer.getUsername());
                // User readyUser = new User(readyPlayer);
                sendReply("update", readyPlayer, new Reply[]{move,new Status(Code.OK)});
            }
        }
    }

}