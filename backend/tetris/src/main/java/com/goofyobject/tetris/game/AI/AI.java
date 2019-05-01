//package com.goofyobject.tetris.domain;
//
//
//import com.goofyobject.tetris.FactoryPiece.Piece;
//import com.goofyobject.tetris.FactoryPiece.PieceFactory;
//import com.goofyobject.tetris.game.FactoryPiece.Piece;
//import com.goofyobject.tetris.game.entity.Board;
//import com.goofyobject.tetris.game.entity.Position;
//// https://zjh776.iteye.com/blog/1979748
//
//
//// SCRATCH!!!
//import java.util.ArrayList;
//
//public class AI {
//
//    private int computerScore;
//    private int humanScore;
//    private String computerID;
//    private String humanID;
//    private final int gridNum = 15;
//    private final int searchDepth = 5; // search depth
//    private final int alpha = 10; // alpha
//    private final int beta = 10; // beta
//    private final int limitNum = 8; //
//
//    public AI(String id) {
//        this.computerID = id;
//    }
//
//    public Position getComputerPosition(Board board) {
//
//        int maxComputerScore = 0;
//        Piece[][] grid = board.getGrid();
//        // if the board is full, there's no pisition to place piece
//        Position result = new Position(100,100);
//        int[][] gridScore = getGridScore(board);
//
//        // return the position with maximum Computer score
//        for (int i = 0; i<= gridNum; i++){
//            for (int j = 0; j<= gridNum; j++){
//                if (grid[i][j] == null && maxComputerScore < gridScore[i][j]){
//                    maxComputerScore = gridScore[i][j];
//                    result = new Position(i, j);
//                    computerScore = maxComputerScore;
//                    break;
//                }
//            }
//        }
//        return result;
//    }
//
//    public int[][] getGridScore(Board board) {
//
//        // TODO: no need to search positions > 3
//        int gridScore[][] = new int[gridNum][gridNum];
//        for (int i = 0; i <= gridNum; i++) {
//            for (int j = 0; j <= gridNum; j++) {
//                if (board.getGrid()[i][j] == null) {
//                    // make a deep copy of board
//                    Board board_copy = board.clone();
//                    Position p = new Position(i, j);
//                    // make sure c is the computer player ID
//                    int c = 1;
//                    board_copy.putPiece(p, c);
//                    gridScore[i][j] = minimax(p, board_copy, searchDepth, alpha, beta, true);
//                }
//            }
//        }
//        return gridScore;
//    }
//
//    public int minimax(Position p, Board board, int searchDepth, int alpha, int beta, boolean AIPlayer){
//
//        int result = 0;
//        Board board_copy = board.clone(); // make a copy of current chess board
//        if (searchDepth == 0 || "GAME END @ position" ) {
//            result = evaluateScore(board);
//        }
//        if (AIPlayer) {
//            int maxScore = Integer.MIN_VALUE;
//            for (int i = 0; i <= gridNum; i++) {
//                for (int j = 0; j <= gridNum; j++) {
//                    if (board.getGrid()[i][j] == null) {
//                        Position pp = new Position(i, j);
//                        int c = 0; // need to find out what is c
//                        board_copy.putPiece(pp, c); //
//                        int score = minimax(pp, board_copy, searchDepth - 1, alpha, beta, false);
//                        maxScore = (maxScore > score)? maxScore : score;
//                        alpha = (alpha > score) ? alpha : score;
//                        if (beta <= alpha) {
//                            break;
//                        }
//                    }
//                }
//            }
//            result = maxScore;
//        } else {
//            int minScore = Integer.MAX_VALUE ;
//            for (int i = 0; i <= gridNum; i++) {
//                for (int j = 0; j <= gridNum; j++) {
//                    if (board.getGrid()[i][j] == null) {
//                        Position pp = new Position(i, j);
//                        int c = 0; // need to find out what is c
//                        board_copy.putPiece(pp, c); //
//                        int score = minimax(pp, board_copy,searchDepth - 1, alpha, beta, true);
//                        minScore = (minScore < score) ? minScore : score;
//                        beta = (beta < score) ? beta : score ;
//                        if (beta <= alpha) {
//                            break;
//                        }
//                    }
//                }
//            }
//           result = minScore;
//        }
//        return result;
//    }
//
//    public int evaluateScore(Board board){
//        // Need to evaluate score as the end of recursion
//        String status = getStatus(board) ;
//        switch(status){
//            case "live_five":
//                return 1000000;
//                break;
//            case "live_four":
//                return 100000;
//                break;
//            case "sleep_four":
//            return 10000;
//            break;
//            case "live_three":
//            return 1000;
//            break;
//            case "live_two":
//            return 100;
//            break;
//            case "sleep_three":
//            return 100;
//            break;
//            case "live_one":
//            return 10;
//            break;
//            case "sleep_two":
//            return 10;
//            break;
//            case "sleep_one":
//            return 1;
//            break;
//            case "un_known":
//            return 0;
//            break;
//        }
//    }
//}
//
//
//
