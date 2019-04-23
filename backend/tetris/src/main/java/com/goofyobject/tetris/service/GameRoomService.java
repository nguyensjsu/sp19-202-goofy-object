package com.goofyobject.tetris.service;

import com.goofyobject.tetris.domain.GameEngine;

public interface GameRoomService {

    public boolean addPlayerToQueue(String username);

    public void removePlayerFromQueue(String username);

    public boolean findOpponent(String username);

    public GameEngine getEngine(String username);

    public String[] getWaitingPlayers();

	public void removePlayersFromGame(String p1, String p2);

}