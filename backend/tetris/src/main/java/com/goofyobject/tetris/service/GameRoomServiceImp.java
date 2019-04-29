package com.goofyobject.tetris.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import com.goofyobject.tetris.game.entity.GameEngine;
import org.springframework.stereotype.Component;

@Component
public class GameRoomServiceImp implements GameRoomService {
    private final LinkedList<String> waitingQueue = new LinkedList<>();
    private final ConcurrentHashMap<String,String> sessionToUser= new  ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, GameEngine> engines = new ConcurrentHashMap<>();

    @Override
    public boolean addPlayerToQueue(String username, String sessionId) {
        synchronized (waitingQueue) {
            if (waitingQueue.contains(username) || engines.contains(username)) {
                return false;
            }

            this.waitingQueue.add(username);
            this.sessionToUser.put(sessionId,username);
            return true;
        }
    }

    @Override
    public void removePlayerFromQueue(String username, String sessionId) {
        if (waitingQueue.contains(username)){
            waitingQueue.remove(username);
        }

        this.sessionToUser.remove(sessionId);
    }

    @Override
    public boolean findOpponent(String curUsername) {

        synchronized (waitingQueue) {
            String opponentUsername = null;
            Iterator<String> iterator = waitingQueue.iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                if (!next.equals(curUsername)) {
                    opponentUsername = next;
                    break;
                }
            }
            // cannot find opponent or player already matched
            if (opponentUsername == null || engines.containsKey(curUsername) || engines.containsKey(opponentUsername)) {
                return false;
            }
            // remove players from queue
            waitingQueue.remove(curUsername);
            waitingQueue.remove(opponentUsername);

            GameEngine engine = new GameEngine(opponentUsername,curUsername);

            engines.put(curUsername, engine);
            engines.put(opponentUsername, engine);
            return true;
        }

    }

    public GameEngine getEngine(String username) {
        return engines.get(username);
    }

    @Override
    public String[] getWaitingPlayers() {
        return (String[]) waitingQueue.toArray();
    }

    @Override
    public void PlayerLeave(String sessionId){
        GameEngine curGame = engines.get(sessionToUser.get(sessionId));
        //set end state;
        //curGame.
    }

    @Override
    public void removePlayersFromGame(String p1, String p2) {
        engines.remove(p1);
        engines.remove(p2);
    }

}