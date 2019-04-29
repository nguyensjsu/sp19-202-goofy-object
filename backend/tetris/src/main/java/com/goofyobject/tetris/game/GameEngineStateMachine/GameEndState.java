package com.goofyobject.tetris.game.GameEngineStateMachine;


import com.goofyobject.tetris.game.entity.Position;

public class GameEndState implements GameState {

    @Override
    public boolean putPiece(String id, Position p) {
        return false;
    }

    @Override
    public String readyPlayer() {
        return null;
    }

    @Override
    public String getPlayerId() {
        return null;
    }
}
