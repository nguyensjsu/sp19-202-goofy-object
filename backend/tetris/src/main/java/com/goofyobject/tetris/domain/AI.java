// package com.goofyobject.tetris.domain;

// import com.goofyobject.tetris.FactoryPiece.Piece;
// import com.goofyobject.tetris.FactoryPiece.PieceFactory;

// // SCRATCH!!!
// import java.util.ArrayList;

// public class AI {

//     private int computerScore;
//     private int humanScore;
//     private String computerID id;
//     private final int gridNum = 15;
//     private final int searchDepth = 5; // search depth
//     private final int alpha = 10; // alpha
//     private final int beta = 10; // beta
//     private final int limitNum = 8; //
    
//     public AI(String id) {
//         this.computerID = id;
//     }

//     public Position getComputerPosition(Board board) {
        
//         int maxComputerScore = 0;
//         // 语法？？
//         int[gridNum][gridNum] = getCSGrid( board );

//         // return the position with maximum Computer score
//         for (int i = 0; i<= gridNum; i++){
//             for (int j = 0; j<= gridNum; j++){
//                 if (board.getGrid[i][j] == null && maxComputerScore < csGrid[i][j]){
//                     maxComputerScore = csGrid[i][j];
//                     Position result = new Position(i, j);
//                     computerScore = maxComputerScore;
//                     break;
//                 }
//                 return new Position(100,100);
//             }
//         }
//         return result;
//     }

//     public int[][] getCSGrid(Board board) {

//         int csGrid[][] = new int[gridNum][gridNum];
//         for (int i = 0; i <= gridNum; i++) {
//             for (int j = 0; j <= gridNum; j++) {
//                 if (board.getGrid[i][j] == null) {
//                     // make a deep copy of board
//                     Board board_copy = board.clone();
//                     Position p = new Position(i, j);
//                     // make sure c is the computer player ID
//                     int c = 1;
//                     board_copy.putPiece(p, c);
//                     csGrid[i][j] = minimax(p, board_copy, searchDepth, alpha, beta, true);
//                 }
//             }
//         }
//         return csGrid;
//     }

//     public int minimax(Position p, Board board, int searchDepth, int alpha, int beta, boolean AIPlayer){

//         int result = 0;
//         Board board_copy = board.clone(); // make a copy of current chess board
//         if (searchDepth == 0 || "GAME END @ position" ) {
//             result = evaluateScore(board);
//         }
//         if (AIPlayer) {
//             int maxScore = Integer.MIN_VALUE;
//             for (int i = 0; i <= gridNum; i++) {
//                 for (int j = 0; j <= gridNum; j++) {
//                     if (board.getGrid[i][j] == null) {
//                         Position pp = new Position(i, j);
//                         int c = 0; // need to find out what is c
//                         board_copy.putPiece(pp, c); //
//                         int score = minimax(pp, board_copy, searchDepth - 1, alpha, beta, false);
//                         int maxScore = max(maxScore, score);
//                         int alpha = max(alpha, score);
//                         if (beta <= alpha) {
//                             break;
//                         }
//                     }
//                 }
//             }
//             int result = maxScore;
//         } else {
//             int minScore = Integer.MAX_VALUE ;
//             for (int i = 0; i <= gridNum; i++) {
//                 for (int j = 0; j <= gridNum; j++) {
//                     if (board.getGrid[i][j] == null) {
//                         Position pp = new Position(i, j);
//                         int c = 0; // need to find out what is c
//                         board_copy.putPiece(pp, c); //
//                         int score = minimax(pp, board_copy,searchDepth - 1, alpha, beta, true);
//                         int minScore = min(minScore, score);
//                         int beta = min(beta, score);
//                         if (beta <= alpha) {
//                             break;
//                         }
//                     }
//                 }
//             }
//            int result = minScore;
//         }
//         return result;
//     }

//     public int evaluateScore(Board board){
//         // Need to evaluate score as the end of recursion
//         String status = getStatus(board) ;
//         switch(status){
//             case "live_five":
//                 return 1000000;
//                 break;
//             case "live_four":
//                 return 100000;
//                 break;
//             case "sleep_four":
//             return 10000;
//             break;
//             case "live_three":
//             return 1000;
//             break;
//             case "live_two":
//             return 100;
//             break;
//             case "sleep_three":
//             return 100;
//             break;
//             case "live_one":
//             return 10;
//             break;
//             case "sleep_two":
//             return 10;
//             break;
//             case "sleep_one":
//             return 1;
//             break;
//             case "un_known":
//             return 0;
//             break;  
//         }
//     }
// }



