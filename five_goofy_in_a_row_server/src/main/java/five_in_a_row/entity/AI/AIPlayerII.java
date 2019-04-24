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

                    /** 向北判断 */
                    code = "";
                    color = 0;
                    for (int m = i, n = j - 1; n > -1; n--) {

                        /** 如果下一位没有棋子就自动跳出程序 */
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

                    /** 向南判断 */
                    code = "";
                    color = 0;
                    for (int m = i, n = j + 1; n < ROW; n++) {

                        /** 如果下一位没有棋子就自动跳出程序 */
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

                    /** 向东北判断 */
                    code = "";
                    color = 0;
                    for (int m = i + 1, n = j - 1; m < COLUMN && n > -1; m++, n--) {

                        /** 如果下一位没有棋子就自动跳出程序 */
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

                    /** 向东南判断 */
                    code = "";
                    color = 0;
                    for (int m = i + 1, n = j + 1; m < COLUMN && n < ROW; m++, n++) {

                        /** 如果下一位没有棋子就自动跳出程序 */
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

                    /** 向西北判断 */
                    code = "";
                    color = 0;
                    for (int m = i - 1, n = j - 1; m > -1 && n > -1; m--, n--) {

                        /** 如果下一位没有棋子就自动跳出程序 */
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

                    /** 向西南判断 */
                    code = "";
                    color = 0;
                    for (int m = i - 1, n = j + 1; m > -1 && n < ROW; m--, n++) {

                        /** 如果下一位没有棋子就自动跳出程序 */
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

        /** 输出位置 */
        // for(int i=0;i<COLUMN;i++){
        //     for(int j=0;j<ROW;j++){
        //         System.out.println(gridWithChess[i][j] + "\t");
        //     }
        //     System.out.println();
        // }
    }

    /** 得到最大值 */
    public void getMax(){
        int n=0;
        int max=0;
        for(int i=0;i<COLUMN;i++){
            for(int j=0;j<ROW;j++){
                if(gridWithChess[i][j]!=0){
                    if(n==0){
                        max=gridWithChess[i][j];
                        a=i;
                        b=j;
                        n++;
                    }else{
                        if(gridWithChess[i][j]>max){
                            max=gridWithChess[i][j];
                            a=i;
                            b=j;
                        }
                    }
                }
            }
        }
        System.out.println("a b" + a + " " + b);
        row.add(a);
        col.add(b);
        if(row.size()%2==1){
            grid[a][b]=1;
        }else{
            grid[a][b]=2;
        }
    }

    /** 下完棋后清空 */
    public void clearChess(){
        for(int i=0;i<COLUMN;i++){
            for(int j=0;j<ROW;j++){
                gridWithChess[i][j]=0;
            }
        }
    }

}