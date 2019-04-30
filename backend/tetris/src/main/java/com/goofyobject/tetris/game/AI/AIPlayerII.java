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

}