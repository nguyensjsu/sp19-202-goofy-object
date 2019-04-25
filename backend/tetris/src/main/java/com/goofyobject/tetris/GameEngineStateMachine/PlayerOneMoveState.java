package com.goofyobject.tetris.GameEngineStateMachine;


import com.goofyobject.tetris.domain.Board;
import com.goofyobject.tetris.domain.Position;

public class PlayerOneMoveState implements GameState {
    private GameEngine engine;
    private String playerId;
    private Board board;

    public PlayerOneMoveState(GameEngine engine, String id, Board board) {
        this.engine = engine;
        this.playerId = id;
        this.board = board;
    }

    @Override
    public boolean putPiece(String id, Position p) {
        if(!playerId.equals(id)) {return false;}
        boolean res = this.board.putPiece(p,1);
        if(res) {
            this.engine.switchToPlayerTwoMoveState();
            return true;
        }
        return false;
    }

    @Override
    public String readyPlayer() {
        return playerId;
    }

    public String getPlayerId() {
        return playerId;
    }
}