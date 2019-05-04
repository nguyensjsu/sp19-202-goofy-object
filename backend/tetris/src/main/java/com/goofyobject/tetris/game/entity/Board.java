package com.goofyobject.tetris.game.entity;

import org.springframework.beans.factory.config.YamlProcessor.MatchCallback;

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

    public int getChessNum(){
        return this.num;
    }
    // get number of pieces around position (i,j)
    public int getNeighNum(Position p){
        int result = 0;
        for (int x = Math.max(p.getX() - 1,0); x <= Math.min(p.getX() + 1, gridNum-1); x++){
            for (int y = Math.max(p.getY() - 1, 0); y <= Math.min(p.getY() + 1, gridNum-1); y++){
                if (this.grid[x][y] !=  null){
                    result += 1;
                }
            }
        }
        return (result -1);
    }
}
