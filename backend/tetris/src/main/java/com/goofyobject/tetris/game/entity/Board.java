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
        // System.out.println("new Put Piece: color=" + c + ", x=" + x + ", y=" + y);
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
        Board bd = new Board();
        bd.num = this.num;
        bd.grid = new Piece[gridNum][gridNum];

        for (int i = 0; i < gridNum ; i++){
            for (int j = 0; j < gridNum ; j++){
                if (this.grid[i][j] != null){
                    int color = this.grid[i][j].getColor();
                    bd.putPiece(new Position(i,j), new Piece(color));
                }
            }
        }
        return bd;
    }
}
