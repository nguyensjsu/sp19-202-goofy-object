package com.goofyobject.tetris.game.AI;

import com.goofyobject.tetris.game.entity.Board;
import com.goofyobject.tetris.game.entity.Position;

// https://zjh776.iteye.com/blog/1979748

// SCRATCH!!!
// import java.util.ArrayList;
import java.util.Random;

// computer forever white   2
// human forever black   1
public class AIPlayerI {
    private Board board;

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
}
