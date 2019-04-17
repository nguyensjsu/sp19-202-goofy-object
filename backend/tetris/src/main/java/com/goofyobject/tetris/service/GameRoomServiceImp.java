package com.goofyobject.tetris.service;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.goofyobject.tetris.domain.GameEngine;
import com.goofyobject.tetris.domain.Player;

import org.springframework.stereotype.Component;

@Component
public class GameRoomServiceImp implements GameRoomService {

    private final ConcurrentLinkedQueue<Player> waitingQueue = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<GameEngine> room = new ConcurrentLinkedQueue<>();


    @Override
    public boolean addPlayer(Player player) {

        if (waitingQueue.contains(player)) {
            return false;
        }

        this.waitingQueue.add(player);
        return true;
    }

    @Override
    public boolean removePlayer(Player player) {

        if (waitingQueue.contains(player)) {
            waitingQueue.remove(player);
            return true;
        }

        return false;
    }

    @Override
    public Player[] getWaitingPlayers() {
        return (Player[]) waitingQueue.toArray();
    }

    @Override
    public GameEngine findRoom(Player player) {
        
        Iterator<GameEngine> it  =  room.iterator();

        while (it.hasNext()){

            GameEngine e = it.next();

            if (e.getPlayer2().getSessionId() == null){
                e.setPlayer2(player);
                return e;
            }
        }

        return null;
        
    }

    @Override
    public void createRoom(Player player) {


        Iterator<GameEngine> it  =  room.iterator();

        while (it.hasNext()){

            GameEngine e = it.next();

            if (e.getPlayer1().getSessionId().equals(player.getSessionId())){
                return;
            }
        }
        
        GameEngine engine = new GameEngine(player, null);

        room.add(engine);
    }

}