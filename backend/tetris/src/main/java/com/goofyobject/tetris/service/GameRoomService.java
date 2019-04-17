package com.goofyobject.tetris.service;

import com.goofyobject.tetris.domain.GameEngine;
import com.goofyobject.tetris.domain.Player;

public interface GameRoomService {

    public boolean addPlayer(Player player);

    public boolean removePlayer(Player player);

    public Player[] getWaitingPlayers();

    public void createRoom(Player player);

    public GameEngine findRoom(Player player);

}