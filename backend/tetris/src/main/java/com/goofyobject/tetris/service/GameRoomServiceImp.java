package com.goofyobject.tetris.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import com.goofyobject.tetris.domain.Game;
import com.goofyobject.tetris.domain.Player;

import org.springframework.stereotype.Component;

@Component
public class GameRoomServiceImp implements GameRoomService {

    private final LinkedList<Player> waitingQueue = new LinkedList<>();
    private final ConcurrentHashMap<String, Game> boards = new ConcurrentHashMap<>();


    @Override
    public boolean addPlayerToQueue(Player player) {

        synchronized(waitingQueue){
            if (waitingQueue.contains(player)) {
                return false;
            }

            this.waitingQueue.add(player);
        }
        return true;
    }

    @Override
    public Player[] getWaitingPlayers() {
        return (Player[]) waitingQueue.toArray();
    }

    @Override
    public synchronized void findRoom(Player player) {
        

        synchronized(waitingQueue){

            Player opponent = null;


            Iterator<Player> iterator = waitingQueue.iterator();

            while (iterator.hasNext()) {
                Player cur = iterator.next();
                if (!cur.getSessionId().equals(player.getSessionId())){
                    opponent = cur;
                }
            }
                //cannot find opponent or player already matched
            if (opponent == null || boards.containsKey(player.getSessionId()) || boards.containsKey(opponent.getSessionId()) ){
                    return;
            }
                //remove players from queue
            waitingQueue.remove(player);
            waitingQueue.remove(opponent);

            Game e = new Game(player, opponent);

            boards.put(player.getSessionId(), e);
            boards.put(opponent.getSessionId(), e);
        }
        
    }

    @Override
    public Game getGameBoard(Player player) {
        return boards.get(player.getSessionId());
    }

}