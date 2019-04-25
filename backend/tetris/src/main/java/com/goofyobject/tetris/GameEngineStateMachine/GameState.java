package com.goofyobject.tetris.GameEngineStateMachine;


import com.goofyobject.tetris.domain.Position;

public interface GameState {

    public boolean putPiece(String id, Position p);

    public String readyPlayer();

    public String getPlayerId();
}
