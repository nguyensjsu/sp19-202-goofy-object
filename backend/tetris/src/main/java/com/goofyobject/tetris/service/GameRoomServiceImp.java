package com.goofyobject.tetris.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import com.goofyobject.tetris.domain.User;
import com.goofyobject.tetris.game.AI.AIContext;
import com.goofyobject.tetris.game.GameEngineStateMachine.GameLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GameRoomServiceImp implements GameRoomService {
    private final LinkedList<User> waitingQueue = new LinkedList<>();
    private final ConcurrentHashMap<User, Object[]> engines = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public boolean addPlayerToQueue(User user, String sessionId) {
        synchronized (waitingQueue) {
            if (waitingQueue.contains(user) || engines.containsKey(user)) {
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
            addPlayersToGame(opponent,user,engine,null);
            return true;
        }

    }

    @Override
    public GameLogic getEngine(User user) {
        if (engines.get(user) == null){
            return null;
        }
        return (GameLogic) engines.get(user)[0];
    }
    
    @Override
    public AIContext getAiContext(User user) {
        if (engines.get(user) == null){
            return null;
        }
        return (AIContext) engines.get(user)[1];
    }

    @Override
    public String[] getWaitingPlayers() {
        return (String[]) waitingQueue.toArray();
    }

    @Override
    public User PlayerLeave(String sessionId){
        User informPlayer = null;
        User leavePlayer = null;
        Iterator<User> iterator = waitingQueue.iterator();
        while (iterator.hasNext()) {
            User next = iterator.next();
            if (next.getSessionId().equals(sessionId)) {
                leavePlayer = next;
                break;
            }
        }

        if (leavePlayer != null){
            removePlayerFromQueue(leavePlayer);
        }

        for (User user : engines.keySet()) {
            if (user.getSessionId().equals(sessionId)){
                GameLogic game = (GameLogic) engines.get(user)[0];
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
    public boolean addPlayersToGame(User p1, User p2, GameLogic game, AIContext context){
        if (p1 != null && engines.containsKey(p1)){
            return false;
        }
        if (p2 != null && engines.containsKey(p2)){
            return false;
        }

        if (context != null){
            System.out.println("1111");
            engines.put(p1,new Object[]{game,context});
        }else{
            System.out.println("2222");
            engines.put(p1,new Object[]{game,null});
            engines.put(p2,new Object[]{game,null});
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