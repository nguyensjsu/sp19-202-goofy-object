package com.goofyobject.tetris.contoller;

import java.util.HashMap;

import com.goofyobject.tetris.domain.GameEngine;
import com.goofyobject.tetris.domain.Move;
import com.goofyobject.tetris.domain.Position;
import com.goofyobject.tetris.domain.ReplyMsg;
import com.goofyobject.tetris.domain.Status;
import com.goofyobject.tetris.domain.User;
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
    public void addUser(SimpMessageHeaderAccessor headerAccessor, User user) throws Exception {

        String username = user.getUsername();
        String sessionId = headerAccessor.getSessionId();

        boolean isAdded = gameRoomService.addPlayerToQueue(username, sessionId);


        ReplyMsg msg = new ReplyMsg(Status.FAIL, user);

        if (!isAdded) {
            sendReply("added",username,msg);
            return;
        }

        msg.setStatus(Status.OK.getValue());
        sendReply("added",username,msg);
        matchOpponent(user,sessionId);
    }

    public void sendReply(String topicName, String username, Object msg){
        messagingTemplate.convertAndSend("/topic/" + topicName + "?" + username, msg);
    }

    public void sendResult(HashMap<String, ReplyMsg> hm){
        for (String username : hm.keySet()) {
            sendReply("update",username,hm.get(username));
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
                Status color = Status.White;

                if (username.equals(p1)){
                    oppnentName = p2;
                    color = Status.Black;
                }


                sendReply("join",username,new ReplyMsg(color, oppnentName));
                return;
            }
            Thread.sleep(500);
            i++;
        }

        gameRoomService.removePlayerFromQueue(username, sessionId);
        sendReply("join",username, new ReplyMsg(Status.FAIL, username));

    }

    @MessageMapping("/putPiece")
    public void putPiece(Move move) throws Exception {

        String username = move.getUsername();

        GameEngine gameEngine = gameRoomService.getEngine(username);

        Position pos = new Position(move.getX(),move.getY());

        if (gameEngine != null && gameEngine.putPiece(username, pos)) {

            HashMap<String,ReplyMsg> hm =  new HashMap<>();

            String p1 = gameEngine.getId1();
            String p2 = gameEngine.getId2();

            String winner = gameEngine.checkWinner(pos);

            if (winner != null) {

                String loser = p1;
                if (winner.equals(p1)){
                    loser = p2;
                }

                hm.put(winner, new ReplyMsg(Status.WIN,move));
                hm.put(loser, new ReplyMsg(Status.LOSE,move));
                sendResult(hm);
                gameRoomService.removePlayersFromGame(p1,p2);
                
            }else if (gameEngine.checkDraw()) {
                hm.put(p1, new ReplyMsg(Status.DRAW,move));
                hm.put(p2, new ReplyMsg(Status.DRAW,move));
                sendResult(hm);
                gameRoomService.removePlayersFromGame(p1,p2);
            }else{
                String readyPlayer = gameEngine.readyPlayer();
                logger.info(readyPlayer);
                // User readyUser = new User(readyPlayer);
                sendReply("update",readyPlayer, new ReplyMsg(Status.OK,move));
            }

        }

    }

}