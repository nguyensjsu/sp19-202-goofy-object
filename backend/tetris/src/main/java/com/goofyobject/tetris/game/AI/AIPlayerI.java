package com.goofyobject.tetris.game.AI;

import com.goofyobject.tetris.game.entity.Board;
import com.goofyobject.tetris.game.entity.Piece;
import com.goofyobject.tetris.game.entity.Position;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

// https://zjh776.iteye.com/blog/1979748

// SCRATCH!!!
// import java.util.ArrayList;
import java.util.Random;

// computer forever white   2
// human forever black   1
@Component
public class AIPlayerI implements AIPlayerIService{

    private int aiColor = 2; //computer fixed to white
    private final int gridNum = 15;
    private final int searchDepth = 3; // search depth
    private final int alpha = 0;
    private final int beta = 0;
    private final String AI_FIVE = "[0,1]*22222[0,1]*";
    private final String AI_FOUR_TWO_LIVE = "022220";
    private final String AI_FIVE_PRE = "122220|022221|0202220|0222020|0220220";
    private final String AI_TRHEE_LIVE = "02220|020220|022020";
    private final String AI_TRHEE_DORMANT = "002221|020221|022021|20022|20202|1022201|122200|122020|120220|1022201";
    private final String AI_TWO_LIVE = "00220|02200|02020|020020|020020";
    private final String AI_TWO_DORMANT = "000221|020021|20002|1020201|1022112|122000|120020|2112201";
    private final String AI_DEAD_FOUR = "122221";
    private final String AI_DEAD_THREE = "12221";
    private final String AI_DEAD_TWO = "1221";
    private final String HUMAN_FIVE = "[0,2]*11111[0,2]*";
    private final String HUMAN_FOUR_TWO_LIVE = "011110";
    private final String HUMAN_FIVE_PRE = "211110|011112|0101110|0111010|0110110";
    private final String HUMAN_TRHEE_LIVE = "01110|010110|011010";
    private final String HUMAN_TRHEE_DORMANT = "001112|010112|011012|10011|10101|2011102|211100|211010|210110|2011102";
    private final String HUMAN_TWO_LIVE = "00110|01100|01010|010010|010010";
    private final String HUMAN_TWO_DORMANT = "000112|010012|10001|2010102|2011221|211000|210010|1221102";
    private final String HUMAN_DEAD_FOUR = "211112";
    private final String HUMAN_DEAD_THREE = "21112";
    private final String HUMAN_DEAD_TWO = "2112";
   
    // for testing connection
    public Position getComputerPosition_2(Board board) {

        Random rn = new Random();
        int x = rn.nextInt(14);
        int y = rn.nextInt(14);
        while (board.getGrid()[x][y] != null) {
            x = rn.nextInt(14);
            y = rn.nextInt(14);
        }
        Position result = new Position(x, y);
        return result;
    }

    // actual method
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
                    if (score > maxComputerScore){
                        maxComputerScore = score;
                        result.setX(i);
                        result.setY(j);
                    }
                }
            }
        }
        return result;
    }

    public int alpha_beta(Board board, int searchDepth, int alpha, int beta, int color) {
        
        int result = 0;
        Board tempBoard = (Board) board.clone();
        // plus condition game not end
        if (searchDepth == 0 ){
            result = evaluateScore(tempBoard, this.aiColor);
            // int score = 0;
            // for (int i = 0 ; i<gridNum; i++){
            //     for (int j =0 ; j<gridNum; i++){
            //         if(board.getGrid()[i][j] == null){
            //             Position p = new Position(i, j);
            //             tempBoard.putPiece(p, new Piece(2));
            //             score = evaluateScore(tempBoard, p, this.aiColor);
            //             result = (score > result) ? score : result;
            //         }
            //     }
            // }
        }
        if (color == 2) {
            int maxScore = Integer.MIN_VALUE;
            for (int i =0 ; i<gridNum; i++){
                for (int j =0 ; j<gridNum; i++){
                    if(board.getGrid()[i][j] == null){
                        tempBoard.putPiece(new Position(i,j), new Piece(2));
                        int score = alpha_beta(tempBoard, searchDepth - 1, alpha, beta, 1);
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
                        tempBoard.putPiece(new Position(i, j), new Piece(1));
                        int score = alpha_beta(tempBoard, searchDepth - 1, alpha, beta, 2);
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

    public int evaluateScore(Board board, int color) {
        int score = 0;
        // 15 Rows
        for (int y = 0; y < gridNum; y++){
            StringBuffer buffer = new StringBuffer();
            for (int x =0 ; x<gridNum; x++){
                buffer = buffer.append(board.getGrid()[x][y].getColor());
            }
            String line = buffer.toString();
            score += getLineScore(line, color);
        }
        // 15 Columns
        for (int x = 0; x < gridNum; x++) {
            StringBuffer buffer = new StringBuffer();
            for (int y = 0; y < gridNum; y++) {
                buffer = buffer.append(board.getGrid()[x][y].getColor());
            }
            String line = buffer.toString();
            score += getLineScore(line, color);
        }
        // 15 Diagonals
        for (int y_offset = 0; y_offset < gridNum; y_offset ++) {
            StringBuffer buffer = new StringBuffer();
            for (int x = 0; x < gridNum; x++) {
                int y = (x - y_offset);
                if (y >= 0 && y < gridNum) {
                    buffer = buffer.append(board.getGrid()[x][y].getColor());
                }
            }
            String line = buffer.toString();
            score += getLineScore(line, color);
        }
        // 14 Diagonals
        for (int y_offset = 1; y_offset < gridNum; y_offset++) {
            StringBuffer buffer = new StringBuffer();
            for (int x = 0; x < gridNum; x++) {
                int y = (x + y_offset);
                if (y >= 0 && y < gridNum) {
                    buffer = buffer.append(board.getGrid()[x][y].getColor());
                }
            }
            String line = buffer.toString();
            score += getLineScore(line, color);
        }
        // 15 Diagonals
        for (int sum = 0; sum < gridNum; sum++) {
            StringBuffer buffer = new StringBuffer();
            for (int x = 0; x < gridNum; x++) {
                int y = sum - x;
                if (y >= 0 && y < gridNum) {
                    buffer = buffer.append(board.getGrid()[x][y].getColor());
                }
            }
            String line = buffer.toString();
            score += getLineScore(line, color);
        } 
        // 14 Diagonals
        for (int sum = gridNum + 1; sum < (2 * gridNum - 1); sum++) {
            StringBuffer buffer = new StringBuffer();
            for (int x = 0; x < gridNum - 1; x++) {
                int y = sum - x;
                if (y >= 0 && y < gridNum) {
                    buffer = buffer.append(board.getGrid()[x][y].getColor());
                }
            }
            String line = buffer.toString();
            score += getLineScore(line, color);
        }           
       
        return score;

    }

    public int getLineScore(String line, int color){
        int score = 0;
        if (color == 2) {
            score += matchNumber(line, AI_FIVE) * 100000;
            score += matchNumber(line, AI_FOUR_TWO_LIVE) * 10000;
            score += matchNumber(line, AI_FIVE_PRE) * 5000;
            score += matchNumber(line, AI_TRHEE_LIVE) * 200;
            score += matchNumber(line, AI_TRHEE_DORMANT) * 50;
            score += matchNumber(line, AI_TWO_LIVE) * 5;
            score += matchNumber(line, AI_TWO_DORMANT) * 3;
            score += matchNumber(line, AI_DEAD_FOUR) * -5;
            score += matchNumber(line, AI_DEAD_THREE) * -5;
            score += matchNumber(line, AI_DEAD_TWO) * -5;
        } else if (color == 1) {
            score += matchNumber(line, HUMAN_FIVE) * 100000;
            score += matchNumber(line, HUMAN_FOUR_TWO_LIVE) * 10000;
            score += matchNumber(line, HUMAN_FIVE_PRE) * 5000;
            score += matchNumber(line, HUMAN_TRHEE_LIVE) * 200;
            score += matchNumber(line, HUMAN_TRHEE_DORMANT) * 50;
            score += matchNumber(line, HUMAN_TWO_LIVE) * 5;
            score += matchNumber(line, HUMAN_TWO_DORMANT) * 3;
            score += matchNumber(line, HUMAN_DEAD_FOUR) * -5;
            score += matchNumber(line, HUMAN_DEAD_THREE) * -5;
            score += matchNumber(line, HUMAN_DEAD_TWO) * -5;
        }
        return score;
    }

    public static int matchNumber(String line, String pattern){
        Pattern p=Pattern.compile(pattern ,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(line);
        int count = 0;
        while(m.find()){ count++;}
        return count;
    }

    // public static void main(String[] args) {

    //     String content = "122220022221";
    //     // System.out.println(matchNumber(content, ".*22222.*"));
    //     int a = matchNumber(content, "122220|022221|0202220|0222020|0220220");
    //     System.out.println(a);

    // }
}


// // NEED TO INPUT EVALUATION FUNCTION
// public int evaluateScore(Board board, Position p, int color){
// int result = 0;
// for (int dir = 0; dir < 8; dir++){
// // FOUR_LIVE
// if(board.getColorAt(p, dir, 1) == color && board.getColorAt(p, dir, 2) ==
// color
// && board.getColorAt(p, dir, 3) == color && board.getColorAt(p, dir, 4) ==
// color
// && board.getColorAt(p, dir, 5) == 0){
// result += 400000;
// }
// // FOUR_DEAD_A
// if (board.getColorAt(p, dir, 1) == color && board.getColorAt(p, dir, 2) ==
// color
// && board.getColorAt(p, dir, 3) == color && board.getColorAt(p, dir, -1) ==
// color
// && board.getColorAt(p, dir, 5) != color ) {
// result += 300000;
// }
// // FOUR_DEAD_B
// if (board.getColorAt(p, dir, -1) == color && board.getColorAt(p, dir, 1) ==
// color
// && board.getColorAt(p, dir, 2) == color && board.getColorAt(p, dir, 3) ==
// color) {
// result += 300000;
// }
// // THREE_LIVE_A
// if (board.getColorAt(p, dir, 1) == color && board.getColorAt(p, dir, 2) ==
// color
// && board.getColorAt(p, dir, -1) == 0 && board.getColorAt(p, dir, -1) == 0) {
// result += 200000;
// }
// // THREE_LIVE_B
// if (board.getColorAt(p, dir, 1) == color && board.getColorAt(p, dir, 2) ==
// color
// && board.getColorAt(p, dir, 3) == 0 && board.getColorAt(p, dir, -1) == 0) {
// result += 200000;
// }
// // TWO_DEAD
// if (board.getColorAt(p, dir, -1) == color && board.getColorAt(p, dir, 1) == 0
// && board.getColorAt(p, dir, 1) == 0 && board.getColorAt(p, dir, 1) == 0) {
// result += 100;
// }
// // More around
// result += (board.getColorAt(p, dir,1) + board.getColorAt(p, dir,2)) * 25;
// }
// return result;
// }