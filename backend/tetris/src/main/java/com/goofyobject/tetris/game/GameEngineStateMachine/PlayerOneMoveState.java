package com.goofyobject.tetris.game.GameEngineStateMachine;


import com.goofyobject.tetris.game.entity.Board;
import com.goofyobject.tetris.game.entity.Position;

public class PlayerOneMoveState implements GameState {
    private GameLogic logic;
    private String playerId;
    private Board board;

    public PlayerOneMoveState(GameLogic logic, String id, Board board) {
        this.logic = logic;
        this.playerId = id;
        this.board = board;
    }

    @Override
    public boolean putPiece(String id, Position p) {
        if(!playerId.equals(id)) {return false;}
        boolean res = this.board.putPiece(p,1);
        if(res) {
            if(this.logic.getAiPlayerI() == null) {
                this.logic.switchToPlayerTwoMoveState();
            }else {
                this.board.putPiece( this.logic.getAiPlayerI().getComputerPosition(), 2);
            }
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
