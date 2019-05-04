package com.goofyobject.tetris.service;

import com.goofyobject.tetris.domain.User;
import com.goofyobject.tetris.game.AI.AIContext;
import com.goofyobject.tetris.game.GameEngineStateMachine.GameLogic;

public interface GameRoomService {

    public boolean addPlayerToQueue(User user, String sessionId);

    public void removePlayerFromQueue(User user);

    public boolean findOpponent(User user);

    public GameLogic getEngine(User user);

    public AIContext getAiContext(User user);

    public String[] getWaitingPlayers();

    public User PlayerLeave(String sessionId);

    public boolean addPlayersToGame(User p1, User p2, GameLogic game, AIContext context);

	public void removePlayersFromGame(User p1, User p2);

}