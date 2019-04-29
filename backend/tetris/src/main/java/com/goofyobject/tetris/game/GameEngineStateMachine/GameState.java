package com.goofyobject.tetris.game.GameEngineStateMachine;


import com.goofyobject.tetris.game.entity.Position;

public interface GameState {

    public boolean putPiece(String id, Position p);

    public String readyPlayer();

    public String getPlayerId();
}
