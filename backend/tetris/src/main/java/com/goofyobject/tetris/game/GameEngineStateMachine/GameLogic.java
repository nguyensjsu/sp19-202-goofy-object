package com.goofyobject.tetris.game.GameEngineStateMachine;

import com.goofyobject.tetris.game.AI.AIPlayerI;
import com.goofyobject.tetris.game.entity.Board;
import com.goofyobject.tetris.game.entity.Piece;
import com.goofyobject.tetris.game.entity.Position;

public class GameLogic {
    private GameState curState;
    private GameState playerOneMoveState;
    private GameState playerTwoMoveState;
    private GameState gameEndState;
    private Board board;
    private AIPlayerI aiPlayerI;

    public GameLogic(String id1, String id2) {
        this.board = new Board();
        if(id2 == null) {
            aiPlayerI = new AIPlayerI(this.board);
            id2 = "AI";
        }
        playerOneMoveState = new PlayerOneMoveState(this, id1, this.board);
        playerTwoMoveState = new PlayerTwoMoveState(this, id2, this.board);
        gameEndState = new GameEndState();
        this.curState = playerOneMoveState;
    }

    public AIPlayerI getAiPlayerI() {
        return aiPlayerI;
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

    public String checkWinner(Position p) {
        int res = this.checkFiveInRow(p);
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
        boolean res = this.board.isFull();
        if(res) {
            this.curState = this.gameEndState;
        }
        return res;
    }

    public String getId1() {
        return this.playerOneMoveState.getPlayerId();
    }

    public String getId2() {
        return this.playerTwoMoveState.getPlayerId();
    }


    // 1 -p1 win, 2- p2 win, 0- no win
    private int checkFiveInRow(Position p) {
        if(p == null) { return 0;}
        Piece[][] grid = this.board.getGrid();
        int gridNum = this.board.getGridNum();
        int x = p.getX();
        int y = p.getY();
        if(x < 0 || y < 0 || x >= gridNum || y >= gridNum) {    return 0;}
        Piece cur = grid[x][y];
        if(cur == null) { return 0;}
        int curColor = cur.getColor();
        int count = 1;

        // check left
        int i = 1;
        while(x-i >= 0 && i < 5) {
            if(grid[x-i][y] != null && grid[x-i][y].getColor() == curColor) {
                count++;
                i++;
            }else {
                break;
            }
        }
        if(count >= 5) {    return curColor;}

        // check right
        i = 1;
        while(x+i < gridNum && i < 5) {
            if(grid[x+i][y] != null && grid[x+i][y].getColor() == curColor) {
                count++;
                i++;
            }else {
                break;
            }
        }
        if(count >= 5) {    return curColor;}

        count = 1;
        // check up
        i = 1;
        while(y-i >= 0  && i < 5) {
            if(grid[x][y-i] != null && grid[x][y-i].getColor() == curColor) {
                count++;
                i++;
            }else {
                break;
            }
        }
        if(count >= 5) {    return curColor;}

        // check down
        i = 1;
        while(y+i < gridNum && i < 5) {
            if(grid[x][y+i] != null && grid[x][y+i].getColor() == curColor) {
                count++;
                i++;
            }else {
                break;
            }
        }
        if(count >= 5) {    return curColor;}

        count = 1;
        // check right-down
        i = 1;
        while(x+i < gridNum && y+i < gridNum && i < 5) {
            if(grid[x+i][y+i] != null && grid[x+i][y+i].getColor() == curColor) {
                count++;
                i++;
            }else {
                break;
            }
        }
        if(count >= 5) {    return curColor;}

        // check left-up
        i = 1;
        while(x-i >= 0 && y-i >= 0 && i < 5) {
            if(grid[x-i ][y-i ] != null && grid[x-i ][y-i ].getColor() == curColor) {
                count++;
                i++;
            }else {
                break;
            }
        }
        if(count >= 5) {    return curColor;}


        count = 1;
        // check left-down
        i = 1;
        while(x-i >= 0 && y+i < gridNum && i < 5) {
            if(grid[x-i][y+i] != null && grid[x-i][y+i].getColor() == curColor) {
                count++;
                i++;
            }else {
                break;
            }
        }
        if(count >= 5) {    return curColor;}


        // check right-up
        i = 1;
        while(x+i < gridNum && y-i >= 0 && i < 5) {
            if(grid[x+i][y-i ] != null && grid[x+i][y-i ].getColor() == curColor) {
                count++;
                i++;
            }else {
                break;
            }
        }
        if(count >= 5) {    return curColor;}

        return 0;
    }
}
