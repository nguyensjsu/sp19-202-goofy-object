package com.goofyobject.tetris.service;

import com.goofyobject.tetris.domain.GameEngine;

public interface GameRoomService {

    public boolean addPlayerToQueue(String sessionId);

    public boolean findOpponent(String sessionId);

    public GameEngine getEngine(String sessionId);

    public String[] getWaitingPlayers();

}