package com.goofyobject.tetris.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import com.goofyobject.tetris.domain.GameEngine;
import org.springframework.stereotype.Component;

@Component
public class GameRoomServiceImp implements GameRoomService {

    private final LinkedList<String> waitingQueue = new LinkedList<>();
    private final ConcurrentHashMap<String, GameEngine> engines = new ConcurrentHashMap<>();

    @Override
    public boolean addPlayerToQueue(String username) {

        synchronized (waitingQueue) {
            if (waitingQueue.contains(username)) {
                return false;
            }

            this.waitingQueue.add(username);
            return true;
        }
    }

    @Override
    public void removePlayerFromQueue(String username) {
        waitingQueue.remove(username);
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
                }
            }
            // cannot find opponent or player already matched
            if (opponentUsername == null || engines.containsKey(curUsername) || engines.containsKey(curUsername)) {
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

}