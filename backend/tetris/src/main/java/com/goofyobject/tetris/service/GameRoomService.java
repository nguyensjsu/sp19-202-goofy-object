package com.goofyobject.tetris.service;

import com.goofyobject.tetris.domain.Game;
import com.goofyobject.tetris.domain.Player;

public interface GameRoomService {

    public boolean addPlayerToQueue(Player player);

    public Player[] getWaitingPlayers();

    public void findRoom(Player player);

    public Game getGameBoard(Player player);

}