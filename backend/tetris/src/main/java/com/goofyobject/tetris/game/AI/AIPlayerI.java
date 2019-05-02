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
    public Position getComputerPosition(Board board) {

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

    // Simple AI
    public Position getComputerPositionSimple(Board board) {

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
                    } else {
                        continue;
                    }
                }
            }
        }
        return result;
    }
    // actual method
    public Position getComputerPosition2(Board board) {

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
                    } else{
                        continue;
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
        if (searchDepth <= 0 ){
            result = evaluateScore(tempBoard, this.aiColor);
            return result;
        }
        else {
            if (color == 2) {
                int maxScore = Integer.MIN_VALUE;
                Position p = this.getComputerPosition(tempBoard);
                int x = 100;
                int y = 100;
                boolean flag = false;
                for (int i = 0 ; i< gridNum; i++){
                    for (int j =0 ; j< gridNum; j++){
                        if(tempBoard.getGrid()[i][j] == null){
                            Board tempBoard2 = (Board) tempBoard.clone(); 
                            tempBoard2.putPiece(new Position(i,j), new Piece(2));
                            int score = alpha_beta(tempBoard2, searchDepth - 1, alpha, beta, 1);
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
                for (int i = 0; i < gridNum; i++) {
                    for (int j = 0; j < gridNum; j++) {
                        if (tempBoard.getGrid()[i][j] == null) {
                            Board tempBoard2 = (Board) tempBoard.clone();
                            tempBoard2.putPiece(new Position(i, j), new Piece(1));
                            int score = alpha_beta(tempBoard2, searchDepth - 1, alpha, beta, 2);
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

    public int evaluateScore(Board board, int color) {
        int score = 0;
        // 15 Rows
        for (int y = 0; y < gridNum; y++){
            StringBuffer buffer = new StringBuffer();
            for (int x =0 ; x<gridNum; x++){
                if (board.getGrid()[x][y] != null){
                    buffer = buffer.append(board.getGrid()[x][y].getColor());
                } else {
                    buffer = buffer.append('0');
                }
            }
            String line = buffer.toString();
            score += getLineScore(line, color);
        }
        // 15 Columns
        for (int x = 0; x < gridNum; x++) {
            StringBuffer buffer = new StringBuffer();
            for (int y = 0; y < gridNum; y++) {
                if (board.getGrid()[x][y] != null) {
                    buffer = buffer.append(board.getGrid()[x][y].getColor());
                } else {
                    buffer = buffer.append('0');
                }
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
                    if (board.getGrid()[x][y] != null) {
                        buffer = buffer.append(board.getGrid()[x][y].getColor());
                    } else {
                        buffer = buffer.append('0');
                    }
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
                    if (board.getGrid()[x][y] != null) {
                        buffer = buffer.append(board.getGrid()[x][y].getColor());
                    } else {
                        buffer = buffer.append('0');
                    }
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
                    if (board.getGrid()[x][y] != null) {
                        buffer = buffer.append(board.getGrid()[x][y].getColor());
                    } else {
                        buffer = buffer.append('0');
                    }
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
                    if (board.getGrid()[x][y] != null) {
                        buffer = buffer.append(board.getGrid()[x][y].getColor());
                    } else {
                        buffer = buffer.append('0');
                    }
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

   // test
    public static void main(String[] args) {

        Board testBoard = new Board();
        AIPlayerIService AIplayer1 = new AIPlayerI();
        testBoard.putPiece(new Position(2,6), new Piece(2));
        testBoard.putPiece(new Position(2,7), new Piece(2));
        testBoard.putPiece(new Position(2, 8), new Piece(2));

        testBoard.putPiece(new Position(6, 4), new Piece(1));
        testBoard.putPiece(new Position(7, 5), new Piece(2));
        testBoard.putPiece(new Position(10, 8), new Piece(2));

        testBoard.putPiece(new Position(5, 7), new Piece(2));
        testBoard.putPiece(new Position(6, 7), new Piece(2));
        testBoard.putPiece(new Position(7, 7), new Piece(2));
        testBoard.putPiece(new Position(8, 7), new Piece(2));
        testBoard.putPiece(new Position(4, 7), new Piece(1));
        testBoard.putPiece(new Position(9, 7), new Piece(1));
        System.out.println("Board Drawed");
        //Position p = AIplayer1.getComputerPositionSimple(testBoard);
        Position p = AIplayer1.getComputerPosition(testBoard);
        System.out.println("new position generated");
        System.out.println(p.getX());
        System.out.println(p.getY());

    }
}