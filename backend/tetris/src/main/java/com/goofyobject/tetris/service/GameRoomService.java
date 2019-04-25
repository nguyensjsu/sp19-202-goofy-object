package com.goofyobject.tetris.service;

import com.goofyobject.tetris.domain.GameEngine;

public interface GameRoomService {

    public boolean addPlayerToQueue(String username, String sessionId);

    public void removePlayerFromQueue(String username, String sessionId);

    public boolean findOpponent(String username);

    public GameEngine getEngine(String username);

    public String[] getWaitingPlayers();

    public void PlayerLeave(String sessionId);

	public void removePlayersFromGame(String p1, String p2);

}