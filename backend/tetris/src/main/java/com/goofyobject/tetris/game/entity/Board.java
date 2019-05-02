package com.goofyobject.tetris.game.entity;


public class Board implements Cloneable{
    private final int gridNum = 15;
    private Piece[][] grid;
    int num = 0;

    public Board() {
        this.grid = new Piece[gridNum][gridNum];
    }

    public boolean putPiece(Position p, Piece piece) {
        int c = piece.getColor();
        if (p == null || (c != 1 && c != 2)) {
            return false;
        }
        int x = p.getX();
        int y = p.getY();
        System.out.println("new Put Piece: color=" + c + ", x=" + x + ", y=" + y);
        if (grid[x][y] == null) {
            grid[x][y] = piece;
            num++;
            return true;
        } else {
            return false;
        }
    }

    // return the grid
    public Piece[][] getGrid() {
        return this.grid;
    }

    public int getGridNum() {
        return gridNum;
    }

    public boolean isFull() {
        return num == gridNum * gridNum;
    }

    // clone
    @Override
    public Board clone() {
        Board bd = null;
        try {
            bd = (Board) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return bd;

    }
}
