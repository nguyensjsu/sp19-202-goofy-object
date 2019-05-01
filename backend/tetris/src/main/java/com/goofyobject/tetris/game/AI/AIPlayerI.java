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
    private final int searchDepth = 4; // search depth
    private final int alpha = 10;
    private final int beta = 100;

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

        int maxComputerScore = Integer.MIN_VALUE;
        Piece[][] grid = board.getGrid();
        // if the board is full, there's no pisition to place piece
        Position result = new Position(100, 100);

        // return the position with maximum Computer score
        for (int i = 0; i < gridNum; i++) {
            for (int j = 0; j < gridNum; j++) {

                if (grid[i][j] == null){
                    Board tempBoard = (Board)this.board.clone();
                    tempBoard.putPiece(new Position(i, j), 2);
                    int score = alpha_beta(tempBoard, searchDepth, alpha, beta,true);
                    if (score > maxComputerScore){
                        maxComputerScore = score;
                        result = new Position(i, j);
                    }
                }

            }
        }
        
        return result;
    }

    public int alpha_beta(Board board, int searchDepth, int alpha, int beta, boolean AIPlayer) {
        
        int result = 0;
        int id = 2;
        Board tempBoard = (Board) this.board.clone();
        // plus condition game not end
        if (searchDepth == 0 ){
            result = evaluateScore(board, id);
        }
        if (AIPlayer) {
            int maxScore = Integer.MIN_VALUE;
            for (int i =0 ; i<gridNum; i++){
                for (int j =0 ; j<gridNum; i++){
                    if(board.getGrid()[i][j] == null){
                        tempBoard.putPiece(new Position(i,j), 2);
                        int score = alpha_beta(tempBoard, searchDepth - 1, alpha, beta, false);
                        maxScore = (maxScore > score) ? maxScore : score;
                        alpha = (alpha > score) ? alpha : score;
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }

            }
            result = maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (int i = 0; i < gridNum; i++) {
                for (int j = 0; j < gridNum; i++) {
                    if (board.getGrid()[i][j] == null) {
                        tempBoard.putPiece(new Position(i, j), 1);
                        int score = alpha_beta(tempBoard, searchDepth - 1, alpha, beta, true);
                        minScore = (minScore < score) ? minScore : score;
                        beta = (beta < score) ? beta : score;
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            result = minScore;
        }
        return result;
    }

    // NEED TO INPUT EVALUATION FUNCTION
    public int evaluateScore(Board board, int id){
        // int result = 0;
        // if(board.checkFiveInRow() == 2){
        //     result = 100000;
        // }
        // return result;
        return 1000;
    }

}
