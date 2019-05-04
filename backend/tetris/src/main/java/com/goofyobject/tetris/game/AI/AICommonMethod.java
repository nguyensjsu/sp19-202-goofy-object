package com.goofyobject.tetris.game.AI;

import com.goofyobject.tetris.game.entity.Board;
import com.goofyobject.tetris.game.entity.Piece;
import com.goofyobject.tetris.game.entity.Position;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

public class AICommonMethod {

    private int aiColor = 2; //computer fixed to white
    private int humanColor = 1; // human fixed to black
    private final int gridNum = 15;
    protected final int searchDepth = 3; // search depth
    protected final int alpha = 0;
    protected final int beta = 0;
    // Piece Patterns
    private final String AI_FIVE = "22222";
    private final String AI_FOUR_TWO_LIVE = "022220";
    private final String AI_FIVE_PRE = "122220|022221|0202220|0222020|0220220";
    private final String AI_TRHEE_LIVE = "02220|020220|022020";
    private final String AI_TRHEE_DORMANT = "002221|020221|022021|20022|20202|1022201|122200|122020|120220|1022201";
    private final String AI_TWO_LIVE = "00220|02200|02020|020020|020020";
    private final String AI_TWO_DORMANT = "000221|020021|20002|1020201|1022112|122000|120020|2112201";
    private final String AI_ONE_LIVE = "021|120";
    private final String AI_DEAD_FOUR = "122221";
    private final String AI_DEAD_THREE = "12221";
    private final String AI_DEAD_TWO = "1221";
    private final String HUMAN_FIVE = "11111";
    private final String HUMAN_FOUR_TWO_LIVE = "011110";
    private final String HUMAN_FIVE_PRE = "211110|011112|0101110|0111010|0110110";
    private final String HUMAN_TRHEE_LIVE = "01110|010110|011010";
    private final String HUMAN_TRHEE_DORMANT = "001112|010112|011012|10011|10101|2011102|211100|211010|210110|2011102";
    private final String HUMAN_TWO_LIVE = "00110|01100|01010|010010|010010";
    private final String HUMAN_TWO_DORMANT = "000112|010012|10001|2010102|2011221|211000|210010|1221102";
    private final String HUMAN_ONE_LIVE = "012|210";
    private final String HUMAN_DEAD_FOUR = "211112";
    private final String HUMAN_DEAD_THREE = "21112";
    private final String HUMAN_DEAD_TWO = "2112";

    public int evaluateScore(Board board, int color) {
        int score = 0;
        int opp = 0;
        if (color == 1){
            opp = 2;
        } else if (color == 2) {opp = 1;}
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
            score += ( getLineScore(line, color) - getLineScore(line, opp));
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
            score += (getLineScore(line, color) - getLineScore(line, opp));
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
            score += (getLineScore(line, color) - getLineScore(line, opp));
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
            score += (getLineScore(line, color) - getLineScore(line, opp));
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
            score += (getLineScore(line, color) - getLineScore(line, opp));
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
            score += (getLineScore(line, color) - getLineScore(line, opp));
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
            score += matchNumber(line, AI_ONE_LIVE) * 1;
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
            score += matchNumber(line, HUMAN_ONE_LIVE) * 1;
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

}