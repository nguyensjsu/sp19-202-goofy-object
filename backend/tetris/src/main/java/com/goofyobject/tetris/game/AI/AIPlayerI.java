package com.goofyobject.tetris.game.AI;

import com.goofyobject.tetris.game.entity.Board;
import com.goofyobject.tetris.game.entity.Piece;
import com.goofyobject.tetris.game.entity.Position;

// https://zjh776.iteye.com/blog/1979748

// SCRATCH!!!
// import java.util.ArrayList;
import java.util.Random;

// computer forever white   2
// human forever black   1
public class AIPlayerI {
    private Board board;
    private int aiColor = 2; //computer fixed to white
    private final int gridNum = 15;



    public AIPlayerI(Board board) {
        this.board = board;
    }    
    // for testing connection
    public Position getComputerPosition() {

        Random rn = new Random();
        int x = rn.nextInt(14);
        int y = rn.nextInt(14);
        while (this.board.getGrid()[x][y] == null) {
            x = rn.nextInt(14);
            y = rn.nextInt(14);
        }
        Position result = new Position(x, y);
        return result;
    }

    // actual method
    public Position getComputerPosition_2() {

        int maxComputerScore = 0;
        Piece[][] grid = board.getGrid();
        // if the board is full, there's no pisition to place piece
        Position result = new Position(100, 100);

        // return the position with maximum Computer score
        for (int i = 0; i < gridNum; i++) {
            for (int j = 0; j < gridNum; j++) {
                if (grid[i][j] == null && maxComputerScore < gridScore[i][j]) {
                    maxComputerScore = gridScore[i][j];
                    result = new Position(i, j);
                    computerScore = maxComputerScore;
                    break;
                }
            }
        }
        return result;
    }


}
