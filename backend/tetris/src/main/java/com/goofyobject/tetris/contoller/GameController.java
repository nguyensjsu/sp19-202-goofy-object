package com.goofyobject.tetris.contoller;

import java.util.HashMap;

import com.goofyobject.tetris.domain.Code;
import com.goofyobject.tetris.domain.ConcreteMessage;
import com.goofyobject.tetris.domain.Move;
import com.goofyobject.tetris.domain.Status;
import com.goofyobject.tetris.domain.User;

import com.goofyobject.tetris.service.GameRoomService;
import com.goofyobject.tetris.game.entity.Position;
import com.goofyobject.tetris.game.entity.GameEngine;

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

        ConcreteMessage replyMessage = new ConcreteMessage();
        user.setDecorator(replyMessage);

        if (!isAdded) {
            Status s = new Status(Code.FAIL);
            s.setDecorator(user);
            HashMap<String,Object> res = new HashMap<>();
            s.getObj(res);
            sendReply("added",username,res);
            return;
        }

        Status s = new Status(Code.OK);
        s.setDecorator(user);
        HashMap<String,Object> res = new HashMap<>();
        s.getObj(res);
        sendReply("added",username,res);

        matchOpponent(user,sessionId);
    }

    public void sendReply(String topicName, String username, Object msg){
        messagingTemplate.convertAndSend("/topic/" + topicName + "?" + username, msg);
    }

    public void sendResult(HashMap<String, Integer> hm, Move move){
        for (String username : hm.keySet()) {
            Status status = new Status(hm.get(username));
            status.setDecorator(move);
            HashMap<String,Object> res = new HashMap<>();
            status.getObj(res);
            sendReply("update",username,res);
        }
    }

    public void matchOpponent(User user, String sessionId) throws Exception {

        String username = user.getUsername();
        int i = 0;
        while (i < 200) {
            gameRoomService.findOpponent(username);
            GameEngine gameEngine = gameRoomService.getEngine(username);

            if (gameEngine != null) {
                String p1 = gameEngine.getId1();
                String p2 = gameEngine.getId2();
                
                String oppnentName = p1;

                Status color = new Status(Code.WHITE);

                if (username.equals(p1)){
                    oppnentName = p2;
                    color = new Status(Code.BLACK);
                }

                color.setDecorator(new User(oppnentName));

                HashMap<String,Object> res = new HashMap<>();
                color.getObj(res);

                sendReply("join",username,res);
                return;
            }
            Thread.sleep(500);
            i++;
        }

        Status fail = new Status(Code.FAIL);
        fail.setDecorator(user);
        HashMap<String,Object> res = new HashMap<>();
        fail.getObj(res);

        gameRoomService.removePlayerFromQueue(username, sessionId);
        sendReply("join",username, res);
    }

    @MessageMapping("/putPiece")
    public void putPiece(Move move) throws Exception {
        String username = move.getUsername();
        GameEngine gameEngine = gameRoomService.getEngine(username);
        Position pos = new Position(move.getX(),move.getY());
        if (gameEngine != null && gameEngine.putPiece(username, pos)) {
            HashMap<String,Integer> hm =  new HashMap<>();

            String p1 = gameEngine.getId1();
            String p2 = gameEngine.getId2();

            String winner = gameEngine.checkWinner(pos);

            if (winner != null) {

                String loser = p1;
                if (winner.equals(p1)){
                    loser = p2;
                }

                hm.put(winner, Code.WIN);
                hm.put(loser, Code.LOSE);
                sendResult(hm,move);
                gameRoomService.removePlayersFromGame(p1,p2);
                
            }else if (gameEngine.checkDraw()) {
                hm.put(p1,Code.DRAW);
                hm.put(p2,Code.DRAW);
                sendResult(hm,move);
                gameRoomService.removePlayersFromGame(p1,p2);
            }else{
                String readyPlayer = gameEngine.readyPlayer();
                logger.info(readyPlayer);
                // User readyUser = new User(readyPlayer);
                Status ok = new Status(Code.OK);
                ok.setDecorator(move);
                HashMap<String,Object> res = new HashMap<>();
                ok.getObj(res);
                sendReply("update",readyPlayer, res);
            }
        }
    }

}