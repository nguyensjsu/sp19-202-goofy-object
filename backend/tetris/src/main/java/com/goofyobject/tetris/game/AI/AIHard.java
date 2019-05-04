package com.goofyobject.tetris.game.AI;

import com.goofyobject.tetris.game.entity.Board;
import com.goofyobject.tetris.game.entity.Piece;
import com.goofyobject.tetris.game.entity.Position;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

// https://zjh776.iteye.com/blog/1979748

// import java.util.ArrayList;
import java.util.Random;

// computer forever white   2
// human forever black   1

public class AIHard extends AICommonMethod implements AIStrategy  {

    // private int aiColor = 2; //computer fixed to white
    // private int humanColor = 1; // human fixed to black
    private final int gridNum = 15;

    // Alpha-Beta AI
    @Override
    public Position getComputerPosition(Board board) {

        int maxComputerScore = Integer.MIN_VALUE;
        Piece[][] grid = board.getGrid();
        // if the board is full, there's no pisition to place piece
        Position result = new Position(100, 100);
        // return the position with maximum Computer score
        for (int i = 0; i < gridNum; i++) {
            for (int j = 0; j < gridNum; j++) {
                if (grid[i][j] == null){
                    Board tempBoard = (Board) board.clone();
                    tempBoard.putPiece(new Position(i, j), new Piece(2));
                    int score = alpha_beta(tempBoard, searchDepth, alpha, beta,2);
                    score -= (Math.abs(i - 7) + Math.abs(j - 7));
                    if (score > maxComputerScore){
                        maxComputerScore = score;
                        result.setX(i);
                        result.setY(j);
                    } else if (score == maxComputerScore){ // if equal score, pick the position randomly
                        Random rn = new Random();
                        int r = rn.nextInt(3);
                        if (r <= 1){
                            result.setX(i);
                            result.setY(j);
                        }
                    }
                    System.out.println("i: " + i + ",j: " + j + ",score : " + score); 
                }
            }
        }
        return result;
    }

    // alpha_beta function to get the score
    public int alpha_beta(Board board, int searchDepth, int alpha, int beta, int color) {
        
        int result = 0;
        Board tempBoard = (Board) board.clone();
        // plus condition game not end
        if (searchDepth <= 0 ){
            return evaluateScore(tempBoard, color);
        }
        else {
            if (color == 2) {
                int maxScore = Integer.MIN_VALUE;
                Position p = this.getComputerPosition(tempBoard);
                boolean flag = false;
                color = 1;
                for (int i = 0 ; i< gridNum; i++){
                    for (int j =0 ; j< gridNum; j++){
                        if(tempBoard.getGrid()[i][j] == null){
                            Board tempBoard2 = (Board) tempBoard.clone(); 
                            tempBoard2.putPiece(new Position(i,j), new Piece(2));
                            int score = alpha_beta(tempBoard2, searchDepth - 1, alpha, beta, color);
                            p = (score > maxScore) ? new Position(i,j) : p;
                            maxScore = (score > maxScore) ? score : maxScore;
                            alpha = (alpha > score) ? alpha : score;
                            if (beta <= alpha) {
                                flag = true;
                                break;
                            }
                        }
                    }
                    if (flag) {
                        break;
                    }
                }
                result = maxScore;
                tempBoard.putPiece(p, new Piece(2));
                return result;
            } else {
                int minScore = Integer.MAX_VALUE;
                Position p = this.getComputerPosition(tempBoard);
                boolean flag = false;
                color = 2;
                for (int i = 0; i < gridNum; i++) {
                    for (int j = 0; j < gridNum; j++) {
                        if (tempBoard.getGrid()[i][j] == null) {
                            Board tempBoard2 = (Board) tempBoard.clone();
                            tempBoard2.putPiece(new Position(i, j), new Piece(1));
                            int score = alpha_beta(tempBoard2, searchDepth - 1, alpha, beta, color);
                            p = (score < minScore) ? new Position(i, j) : p;
                            minScore = (score < minScore) ? score : minScore;
                            beta = (beta < score) ? beta : score;
                            if (beta <= alpha) {
                                flag = true;
                                break;
                            }
                        }
                    }
                    if (flag) {
                        break;
                    }
                }
                result = minScore;
                tempBoard.putPiece(p, new Piece(1));
                return result;
            }
        }
    }



    public static void main(String[] args) {

        Board testBoard = new Board();
        AIPlayerIService AIplayer1 = new AIPlayerI();
        // testBoard.putPiece(new Position(6, 6), new Piece(1));
        // testBoard.putPiece(new Position(7, 7), new Piece(1));
        // testBoard.putPiece(new Position(8, 8), new Piece(1));
        // testBoard.putPiece(new Position(5, 5), new Piece(1));
        // testBoard.putPiece(new Position(4, 4), new Piece(2));
        // testBoard.putPiece(new Position(6, 7), new Piece(2));
        // testBoard.putPiece(new Position(6, 8), new Piece(2));

        System.out.println("Board Drawed");
        int score_AI = AIplayer1.evaluateScore(testBoard, 2);
        int score_HUMAN = AIplayer1.evaluateScore(testBoard, 1);
        System.out.println("AI Score: " + score_AI + ", Human Score: " + score_HUMAN);
       // testBoard.putPiece(new Position(6, 9), new Piece(2));

        // testBoard.putPiece(new Position(2,7), new Piece(2));
        // testBoard.putPiece(new Position(2, 8), new Piece(2));

        // testBoard.putPiece(new Position(6, 4), new Piece(1));
        // testBoard.putPiece(new Position(7, 5), new Piece(2));
        // testBoard.putPiece(new Position(10, 8), new Piece(2));

        // testBoard.putPiece(new Position(5, 7), new Piece(2));
        // testBoard.putPiece(new Position(6, 7), new Piece(2));
        // testBoard.putPiece(new Position(7, 7), new Piece(2));
        // testBoard.putPiece(new Position(8, 7), new Piece(2));
        // testBoard.putPiece(new Position(4, 7), new Piece(1));
        // testBoard.putPiece(new Position(3, 7), new Piece(1));
        
        Position p = AIplayer1.getComputerPositionSimple(testBoard);
        //Position p = AIplayer1.getComputerPosition2(testBoard);
        System.out.println("new position generated");
        System.out.println(p.getX());
        System.out.println(p.getY());
    }
}