package com.goofyobject.tetris.game.AI;

import com.goofyobject.tetris.game.entity.Board;
import com.goofyobject.tetris.game.entity.Piece;
import com.goofyobject.tetris.game.entity.Position;


import java.util.Random;

public class AIMedium extends AICommonMethod implements AIStrategy {

    private final int gridNum = 15;

    public Position getComputerPosition(Board board) {

        int maxComputerScore = Integer.MIN_VALUE;
        Piece[][] grid = board.getGrid();
        // if the board is full, there's no pisition to place piece
        Position result = new Position(100, 100);
        // return the position with maximum Computer score
        for (int i = 0; i < gridNum; i++) {
            for (int j = 0; j < gridNum; j++) {
                if (grid[i][j] == null) {
                    Board tempBoard = (Board) board.clone();
                    tempBoard.putPiece(new Position(i, j), new Piece(2));
                    int score = evaluateScore(tempBoard, 2);
                    score -= (Math.abs(i-7) + Math.abs(j-7));
                    if (score > maxComputerScore) {
                        maxComputerScore = score;
                        result.setX(i);
                        result.setY(j);
                    } else if (score == maxComputerScore) { // if equal score, pick the position randomly
                        Random rn = new Random();
                        int r = rn.nextInt(3);
                        if (r <= 1) {
                            result.setX(i);
                            result.setY(j);
                        }
                    }
                    System.out.println("i: " + i + ",j: " + j + ",score : " + score );
                }
            }
        }
        return result;
    }

}