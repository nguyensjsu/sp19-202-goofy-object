package com.goofyobject.tetris.game.entity;


public class Board {
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
        return grid;
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

    // from center Position p, move direction dir, and offset number p, return piece color
    // 1: black, 2: white, 3: blank
    public int getColorAt(Position p, int dir, int offset) {
        int i = p.getX();
        int j = p.getY();
        int result = 0;

        switch (dir) {
            case 1:
                j -= offset;
                break;
            case 2:
                i += offset;
                j -= offset;
                break;
            case 3:
                i += offset;
                break;
            case 4:
                i += offset;
                j += offset;
                break;
            case 5:
                j += offset;
                break;
            case 6:
                i -= offset;
                j += offset;
                break;
            case 7:
                i -= offset;
                break;
            case 8:
                i -= offset;
                j -= offset;
            break;            
            default:
                break;
        }
        if ( i >=0 && i <= 14 && j >= 0 && j <= 14){
            if (this.grid[i][j] == null) {
                result =  this.grid[i][j].getColor();
            } else {
                result = 0;
            }
        } else {
            result = -1;  // out of boudary
        }
        return result;
        
    }
}
