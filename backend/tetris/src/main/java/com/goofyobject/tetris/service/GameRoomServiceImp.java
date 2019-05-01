package com.goofyobject.tetris.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import com.goofyobject.tetris.domain.User;
import com.goofyobject.tetris.game.GameEngineStateMachine.GameLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GameRoomServiceImp implements GameRoomService {
    private final LinkedList<User> waitingQueue = new LinkedList<>();
    private final ConcurrentHashMap<User, GameLogic> engines = new ConcurrentHashMap<>();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public boolean addPlayerToQueue(User user, String sessionId) {
        synchronized (waitingQueue) {
            if (waitingQueue.contains(user) || engines.contains(user)) {
                return false;
            }

            this.waitingQueue.add(user);
            return true;
        }
    }

    @Override
    public void removePlayerFromQueue(User user) {
        if (waitingQueue.contains(user)){
            waitingQueue.remove(user);
        }
    }

    @Override
    public boolean findOpponent(User user) {
        synchronized (waitingQueue) {
            User opponent = null;
            Iterator<User> iterator = waitingQueue.iterator();
            while (iterator.hasNext()) {
                User next = iterator.next();
                if (!next.equals(user)) {
                    opponent = next;
                    break;
                }
            }
            // cannot find opponent or player already matched
            if (opponent == null || engines.containsKey(user) || engines.containsKey(opponent)) {
                return false;
            }
            // remove players from queue
            waitingQueue.remove(user);
            waitingQueue.remove(opponent);

            String opponentUsername = opponent.getUsername();
            String curUsername = user.getUsername();
            GameLogic engine = new GameLogic(opponentUsername,curUsername);
            addPlayersToGame(opponent,user, engine);
            return true;
        }

    }

    public GameLogic getEngine(User user) {
        return engines.get(user);
    }

    @Override
    public String[] getWaitingPlayers() {
        return (String[]) waitingQueue.toArray();
    }

    @Override
    public User PlayerLeave(String sessionId){

        User informPlayer = null; 

        for (User user : engines.keySet()) {
            if (user.getSessionId().equals(sessionId)){

                

                GameLogic game = engines.get(user);
                String player1Username = game.getId1();
                String player2Username = game.getId2();
                User player1 = new User(player1Username);
                User player2 = null;
                if (player2Username != null){
                    player2 = new User(game.getId2());
                }
                informPlayer = user.getUsername().equals(player1Username) ? player2 : player1; 
                removePlayersFromGame(player1,player2);

            }
        }
        return informPlayer;
    }


    @Override
    public boolean addPlayersToGame(User p1, User p2, GameLogic game){

        if (p1 != null && engines.contains(p1)){
            return false;
        }
        if (p2 != null && engines.contains(p2)){
            return false;
        }

        engines.put(p1,game);
        if (p2 != null){
            engines.put(p2,game);
        }
        return true;
    }

    @Override
    public void removePlayersFromGame(User p1, User p2) {

        if (p1 != null){
            engines.remove(p1);
        }
        if (p2 != null){
            engines.remove(p2);
        }

        logger.info("current games: "+ engines.toString());
    }

}