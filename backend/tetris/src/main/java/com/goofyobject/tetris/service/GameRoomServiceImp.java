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
    public boolean addPlayerToQueue(String sessionId) {

        synchronized (waitingQueue) {
            if (waitingQueue.contains(sessionId)) {
                return false;
            }

            this.waitingQueue.add(sessionId);
            return true;
        }
    }

    @Override
    public void removePlayerFromQueue(String sessionId) {
        waitingQueue.remove(sessionId);
    }

    @Override
    public boolean findOpponent(String curSessionId) {

        synchronized (waitingQueue) {

            String opponentSessionID = null;

            Iterator<String> iterator = waitingQueue.iterator();

            while (iterator.hasNext()) {

                String next = iterator.next();

                if (!next.equals(curSessionId)) {
                    opponentSessionID = next;
                }
            }
            // cannot find opponent or player already matched
            if (opponentSessionID == null || engines.containsKey(curSessionId) || engines.containsKey(curSessionId)) {
                return false;
            }
            // remove players from queue
            waitingQueue.remove(curSessionId);
            waitingQueue.remove(opponentSessionID);

            GameEngine engine = new GameEngine(curSessionId, opponentSessionID);

            engines.put(curSessionId, engine);
            engines.put(opponentSessionID, engine);

            return true;
        }

    }

    public GameEngine getEngine(String sessionId) {
        return engines.get(sessionId);
    }

    @Override
    public String[] getWaitingPlayers() {
        return (String[]) waitingQueue.toArray();
    }

}