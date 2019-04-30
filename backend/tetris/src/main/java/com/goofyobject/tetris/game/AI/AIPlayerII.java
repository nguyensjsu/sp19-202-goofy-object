package com.goofyobject.tetris.game.AI;

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

    
}