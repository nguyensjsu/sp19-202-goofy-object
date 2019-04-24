package com.goofyobject.tetris.GameEngineStateMachine;

import com.goofyobject.tetris.domain.Board;
import com.goofyobject.tetris.domain.Position;

public class GameEngine {
    private GameState curState;
    private GameState playerOneMoveState;
    private GameState playerTwoMoveState;
    private GameState gameEndState;
    private Board board;

    public GameEngine(String id1, String id2) {
        this.board = new Board();
        playerOneMoveState = new PlayerOneMoveState(this, id1, this.board);
        playerTwoMoveState = new PlayerTwoMoveState(this, id2, this.board);
        gameEndState = new GameEndState();
        this.curState = playerOneMoveState;
    }

    public boolean putPiece(String id, Position p) {
        return this.curState.putPiece(id, p);
    }

    public String readyPlayer() {
        return this.curState.readyPlayer();
    }

    public void switchToPlayerOneMoveState() {
        this.curState = playerOneMoveState;
    }

    public void switchToPlayerTwoMoveState() {
        this.curState = playerTwoMoveState;
    }

    private String checkWinner(Position p) {
        int res = this.board.checkFiveInRow(p);
        if(res == 1) {
            this.curState = this.gameEndState;
            return playerOneMoveState.getPlayerId();
        }else if(res == 2) {
            this.curState = this.gameEndState;
            return playerTwoMoveState.getPlayerId();
        }else {
            return null;
        }
    }

    public boolean checkDraw() {
        boolean res = this.board.checkDraw();
        if(res) {
            this.curState = this.gameEndState;
        }
        return res;
    }
}
