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
import com.goofyobject.tetris.game.GameEngineStateMachine.GameLogic;
import com.goofyobject.tetris.game.AI.AIPlayerIService;
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

    @Autowired
    private AIPlayerIService AIplayer1;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @MessageMapping("/createAiGame")
    public void createAiGame(SimpMessageHeaderAccessor headerAccessor, User user) throws Exception {
        String username = user.getUsername();
        String sessionId = headerAccessor.getSessionId();
        user.setSessionId(sessionId);
        boolean isAdded = gameRoomService.addPlayersToGame(user, null, new GameLogic(username, null));

        if (!isAdded) {
            sendReply("added",user, new Reply[]{new Status(Code.FAIL)});
            return;
        }
        sendReply("join",user,new Reply[]{new Status(Code.BLACK)});
    }

    @MessageMapping("/addToQueue")
    public void addUser(SimpMessageHeaderAccessor headerAccessor, User user) throws Exception {
        String username = user.getUsername();
        logger.info("-----------username:" + username);
        String sessionId = headerAccessor.getSessionId();
        user.setSessionId(sessionId);

        boolean isAdded = gameRoomService.addPlayerToQueue(user, sessionId);

        if (!isAdded) {
            sendReply("added",user, new Reply[]{new Status(Code.FAIL)});
            return;
        }
        sendReply("added",user,new Reply[]{new Status(Code.OK)});
        matchOpponent(user);
    }

    public void sendReply(String topicName, User user, Reply[] reply){
        Reply cur = user;
        for (int i = 0; i < reply.length; i++){
            cur.setDecorator(reply[i]);
            cur = reply[i];
        }
        cur.setDecorator(new ConcreteMessage());
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

    public void matchOpponent(User user) throws Exception {
        String username = user.getUsername();
        int i = 0;
        while (i < 200) {
            gameRoomService.findOpponent(user);
            GameLogic gameLogic = gameRoomService.getEngine(user);

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
        gameRoomService.removePlayerFromQueue(user);
        sendReply("join",user, new Reply[]{new Status(Code.FAIL)});
    }

    @MessageMapping("/putPiece")
    public void putPiece(Move move) throws Exception {
        String username = move.getUsername();
        User user = new User(username);
        GameLogic gameLogic = gameRoomService.getEngine(user);
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
                gameRoomService.removePlayersFromGame(new User(p1), new User(p2));
            }else if (gameLogic.checkDraw()) {
                hm.put(p1,Code.DRAW);
                hm.put(p2, Code.DRAW);
                
                sendResult(hm,move);
                gameRoomService.removePlayersFromGame(new User(p1), new User(p2));
            }else{
                if(gameLogic.isAI()) {
                    Position AIPosition = AIplayer1.getComputerPosition(gameLogic.getBoard());
                    //Position AIPosition = AIplayer1.getComputerPositionSimple(gameLogic.getBoard());
                    // gameLogic.putPiece("AI", AIPosition);
                    move = new Move("AI", AIPosition.getX(), AIPosition.getY());
                    putPiece(move);
                }else {
                    User readyPlayer = new User(gameLogic.readyPlayer());
                    logger.info(readyPlayer.getUsername());
                    //sendReply("update", readyPlayer, new Reply[]{move,new Status(Code.OK)});
                }
            }
        }
    }


    @MessageMapping("/putPieceReal")
    public void putPieceReal(Move move) throws Exception {
        String username = move.getUsername();
        User user = new User(username);
        GameLogic gameLogic = gameRoomService.getEngine(user);
        Position pos = new Position(move.getX(),move.getY());
        if (gameLogic != null && gameLogic.putPiece(username, pos)) {
            String p1 = gameLogic.getId1();
            if(gameLogic.isAI()) {
                singlePlay(p1, gameLogic, pos);
            }else {
                String p2 = gameLogic.getId2();
                battlePlay(p1, p2, gameLogic, pos);
            }
        }
    }

    private void singlePlay(String p1, GameLogic gameLogic,Position pos) {
        String winner = gameLogic.checkWinner(pos);
        HashMap<String,Integer> hm =  new HashMap<>();
        Move move = new Move(p1, pos.getX(), pos.getY());
        if (winner != null) {
            if (winner.equals(p1+"AI")){
                hm.put(p1, Code.LOSE);
            }else {
                hm.put(p1, Code.WIN);
            }
            sendResult(hm, move);
            gameRoomService.removePlayersFromGame(new User(p1), null);
        }else if (gameLogic.checkDraw()) {
            hm.put(p1,Code.DRAW);
            sendResult(hm, move);
            gameRoomService.removePlayersFromGame(new User(p1), null);
        }else{
            if(gameLogic.isAI()  && gameLogic.readyPlayer().equals(p1+"AI")) {
                Position AIPosition = AIplayer1.getComputerPosition(gameLogic.getBoard());
                gameLogic.putPiece(null, AIPosition);
                singlePlay(p1, gameLogic,AIPosition);
            }else {
                move = new Move(null, pos.getX(), pos.getY());
                User readyPlayer = new User(gameLogic.readyPlayer());
                logger.info(readyPlayer.getUsername());
                sendReply("update", readyPlayer, new Reply[]{move,new Status(Code.OK)});
            }
        }
    }

    private void battlePlay(String p1, String p2, GameLogic gameLogic, Position pos) {
        String winner = gameLogic.checkWinner(pos);
        HashMap<String,Integer> hm =  new HashMap<>();
        Move move = new Move(p1, pos.getX(), pos.getY());
        if (winner != null) {
            String loser = p1;
            if (winner.equals(p1)){
                loser = p2;
            }

            hm.put(winner, Code.WIN);
            hm.put(loser, Code.LOSE);

            sendResult(hm, move);
            gameRoomService.removePlayersFromGame(new User(p1), new User(p2));
        }else if (gameLogic.checkDraw()) {
            hm.put(p1,Code.DRAW);
            hm.put(p2, Code.DRAW);
            sendResult(hm,move);
            gameRoomService.removePlayersFromGame(new User(p1), new User(p2));
        }else{
            User readyPlayer = new User(gameLogic.readyPlayer());
            logger.info(readyPlayer.getUsername());
            sendReply("update", readyPlayer, new Reply[]{move,new Status(Code.OK)});

        }
    }

}