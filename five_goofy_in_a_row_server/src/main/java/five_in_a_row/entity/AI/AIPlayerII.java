package five_in_a_row.entity.AI;

import java.util.HashMap;
import java.util.LinkedList;

public class AIPlayerII implements Data {

    private int a;
    private int b;

    private int[][] grid;
    private LinkedList<Integer> row;
    private LinkedList<Integer> col;

    private int[][] gridWithChess = new int[15][15];

    private HashMap<String, Integer> hm = new HashMap<>();

    public AIPlayerII() {

    }

    public AIPlayerII(int[][] grid, LinkedList<Integer> row, LinkedList<Integer> col) {
        super();

        this.grid = grid;
        this.row = row;
        this.col = col;
        setChess();
    }

    public void setChess() {

        /** Black chess */
        hm.put("1", 12);
        hm.put("11", 120);
        hm.put("111", 1200);
        hm.put("1111", 10000);
        hm.put("12", 10);
        hm.put("112", 100);
        hm.put("1112", 1000);
        hm.put("11112", 10000);

        /** White chess */
        hm.put("2", 21);
        hm.put("22", 210);
        hm.put("222", 2100);
        hm.put("2222", 20000);
        hm.put("21", 20);
        hm.put("221", 200);
        hm.put("2221", 2000);
        hm.put("22221", 20000);
    }

    public void AI() {
        for (int i = 0; i < COLUMN; i++) {
            for (int j = 0; j < ROW; j++) {

                /** 当前位置没有棋子 */
                if (grid[i][j] == 0) {

                    /** 向东判断 */
                    String code = "";
                    int color = 0;
                    for (int m = i + 1, n = j; m < COLUMN; m++) {

                        /** 如果下一位没有棋子就跳出程序 */
                        if (grid[m][n] == 0) {
                            break;
                        } else {

                            /** 判断是否是第一颗棋子 */
                            if (color == 0) {
                                color = grid[m][n];
                                code += grid[m][n];
                            } else {

                                /** 判断颜色是否相同 */
                                if (color == grid[m][n]) {
                                    code += grid[m][n];
                                } else {
                                    code += grid[m][n];
                                    break;
                                }
                            }
                        }
                    }

                    Integer value = hm.get(code);
                    if (value != null) {
                        gridWithChess[i][j] += value;
                    }

                    /** 向西判断 */
                    code = "";
                    color = 0;
                    for (int m = i - 1, n = j; m > -1; m--) {

                        /** 如果下一位没有棋子就跳出程序 */
                        if (grid[m][n] == 0) {
                            break;
                        } else {

                            /** 判断是否为第一颗棋子 */
                            if (color == 0) {
                                color = grid[m][n];
                                code += grid[m][n];
                            } else {

                                /** 判断颜色是否相同 */
                                if (color == grid[m][n]) {
                                    code += grid[m][n];
                                } else {
                                    code += grid[m][n];
                                    break;
                                }
                            }
                        }
                    }

                    value = hm.get(code);
                    if (value != null) {
                        gridWithChess[i][j] += value;
                    }

                    
                }
            }
        }
    }

}