package com.goofyobject.tetris.game.ai;

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

                /** No Piece */
                if (grid[i][j] == 0) {

                    /** East Direction */
                    String code = "";
                    int color = 0;
                    for (int m = i + 1, n = j; m < COLUMN; m++) {

                        /** if no Piece at next postion */
                        if (grid[m][n] == 0) {
                            break;
                        } else {

                            /** First Piece? */
                            if (color == 0) {
                                color = grid[m][n];
                                code += grid[m][n];
                            } else {

                                /** Color Same? */
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

                    /** West Direction */
                    code = "";
                    color = 0;
                    for (int m = i - 1, n = j; m > -1; m--) {

                        /** If no Piece at next Position, break */
                        if (grid[m][n] == 0) {
                            break;
                        } else {

                            /** First Piece? */
                            if (color == 0) {
                                color = grid[m][n];
                                code += grid[m][n];
                            } else {

                                /** Same Color? */
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

                    /** North Direciton */
                    code = "";
                    color = 0;
                    for (int m = i, n = j - 1; n > -1; n--) {

                        /** Break if no Piece */
                        if (grid[m][n] == 0) {
                            break;
                        } else {

                            /** First Piece? */
                            if (color == 0) {
                                color = grid[m][n];
                                code += grid[m][n];
                            } else {

                                /** Same Color? */
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

                    /** South Direction */
                    code = "";
                    color = 0;
                    for (int m = i, n = j + 1; n < ROW; n++) {

                        /** Break if no Piece */
                        if (grid[m][n] == 0) {
                            break;
                        } else {

                            /** First Piece? */
                            if (color == 0) {
                                color = grid[m][n];
                                code += grid[m][n];
                            } else {

                                /** Same Color? */
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
                    /** NorthEast */
                    code = "";
                    color = 0;
                    for (int m = i + 1, n = j - 1; m < COLUMN && n > -1; m++, n--) {

                        
                        if (grid[m][n] == 0) {
                            break;
                        } else {

                            
                            if (color == 0) {
                                color = grid[m][n];
                                code += grid[m][n];
                            } else {

                                
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
                    /** SouthEast */
                    code = "";
                    color = 0;
                    for (int m = i + 1, n = j + 1; m < COLUMN && n < ROW; m++, n++) {

                        
                        if (grid[m][n] == 0) {
                            break;
                        } else {

                            if (color == 0) {
                                color = grid[m][n];
                                code += grid[m][n];
                            } else {

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
                    /** NorthWest */
                    code = "";
                    color = 0;
                    for (int m = i - 1, n = j - 1; m > -1 && n > -1; m--, n--) {

                        if (grid[m][n] == 0) {
                            break;
                        } else {

                            if (color == 0) {
                                color = grid[m][n];
                                code += grid[m][n];
                            } else {

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
                    code = "";
                    color = 0;
                    for (int m = i - 1, n = j + 1; m > -1 && n < ROW; m--, n++) {

                        if (grid[m][n] == 0) {
                            break;
                        } else {

                            if (color == 0) {
                                color = grid[m][n];
                                code += grid[m][n];
                            } else {

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

        /** Output */
        // for(int i=0;i<COLUMN;i++){
        // for(int j=0;j<ROW;j++){
        // System.out.println(gridWithChess[i][j] + "\t");
        // }
        // System.out.println();
        // }
    }

    /** getMax */
    public void getMax() {
        int n = 0;
        int max = 0;
        for (int i = 0; i < COLUMN; i++) {
            for (int j = 0; j < ROW; j++) {
                if (gridWithChess[i][j] != 0) {
                    if (n == 0) {
                        max = gridWithChess[i][j];
                        a = i;
                        b = j;
                        n++;
                    } else {
                        if (gridWithChess[i][j] > max) {
                            max = gridWithChess[i][j];
                            a = i;
                            b = j;
                        }
                    }
                }
            }
        }
        System.out.println("a b" + a + " " + b);
        row.add(a);
        col.add(b);
        if (row.size() % 2 == 1) {
            grid[a][b] = 1;
        } else {
            grid[a][b] = 2;
        }
    }

    /** clearChessBoard */
    public void clearChess() {
        for (int i = 0; i < COLUMN; i++) {
            for (int j = 0; j < ROW; j++) {
                gridWithChess[i][j] = 0;
            }
        }
    }
}