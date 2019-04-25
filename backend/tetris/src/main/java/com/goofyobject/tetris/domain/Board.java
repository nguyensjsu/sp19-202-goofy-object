package com.goofyobject.tetris.domain;

import com.goofyobject.tetris.FactoryPiece.Piece;
import com.goofyobject.tetris.FactoryPiece.PieceFactory;

public class Board {
    private final int gridNum = 15;
    private Piece[][] grid;
    int num = 0;

    public Board() {
        this.grid = new Piece[gridNum][gridNum];
    }

    public boolean putPiece(Position p, int c) {
        if(p == null || (c != 1 && c != 2)) { return false;}
        int x = p.getX();
        int y = p.getY();
        if( grid[x][y] == null ) {
            grid[x][y] = PieceFactory.getNewPiece(c);
            num++;
            return true;
        }else {
            return false;
        }
    }

    public boolean checkDraw() {
        return num == gridNum * gridNum;
    }

    // 1 -p1 win, 2- p2 win, 0- no win
    public int checkFiveInRow(Position p) {
        if(p == null) { return 0;}
        int x = p.getX();
        int y = p.getY();
        if(x < 0 || y < 0 || x >= gridNum || y >= gridNum) {    return 0;}
        Piece cur = grid[x][y];
        if(cur == null) { return 0;}
        int curColor = cur.getColor();
        int count = 1;

        // check left
        int i = 1;
        while(x-i >= 0 && i < 5) {
            if(grid[x-i][y] != null && grid[x-i][y].getColor() == curColor) {
                count++;
                i++;
            }else {
                break;
            }
        }
        if(count >= 5) {    return curColor;}

        // check right
        i = 1;
        while(x+i < gridNum && i < 5) {
            if(grid[x+i][y] != null && grid[x+i][y].getColor() == curColor) {
                count++;
                i++;
            }else {
                break;
            }
        }
        if(count >= 5) {    return curColor;}

	    count = 1;
        // check up
        i = 1;
        while(y-i < gridNum && i < 5) {
            if(grid[x][y-i] != null && grid[x][y-i].getColor() == curColor) {
                count++;
                i++;
            }else {
                break;
            }
        }
        if(count >= 5) {    return curColor;}

        // check down
        i = 1;
        while(y+i < gridNum && i < 5) {
            if(grid[x][y+i] != null && grid[x][y+i].getColor() == curColor) {
                count++;
                i++;
            }else {
                break;
            }
        }
        if(count >= 5) {    return curColor;}

	    count = 1;
        // check right-down
        i = 1;
        while(x+i < gridNum && y+i < gridNum && i < 5) {
            if(grid[x+i][y+i] != null && grid[x+i][y+i].getColor() == curColor) {
                count++;
                i++;
            }else {
                break;
            }
        }
        if(count >= 5) {    return curColor;}

	// check left-up
        i = 1;
        while(x-i >= 0 && y-i >= 0 && i < 5) {
            if(grid[x-i ][y-i ] != null && grid[x-i ][y-i ].getColor() == curColor) {
                count++;
                i++;
            }else {
                break;
            }
        }
        if(count >= 5) {    return curColor;}


	    count = 1;
        // check left-down
        i = 1;
        while(x-i >= 0 && y+i < gridNum && i < 5) {
            if(grid[x-i][y+i] != null && grid[x-i][y+i].getColor() == curColor) {
                count++;
                i++;
            }else {
                break;
            }
        }
        if(count >= 5) {    return curColor;}


        // check right-up
        i = 1;
        while(x+i < gridNum && y-i >= 0 && i < 5) {
            if(grid[x+i][y-i ] != null && grid[x+i][y-i ].getColor() == curColor) {
                count++;
                i++;
            }else {
                break;
            }
        }
        if(count >= 5) {    return curColor;}

        return 0;
    }

}
