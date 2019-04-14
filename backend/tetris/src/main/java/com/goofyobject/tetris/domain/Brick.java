package com.goofyobject.tetris.domain;

public class Brick {

    private int x;
    private int y;

    public Brick(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int[] getAxis(){
        return new int[] {x,y};
    }

    public void moveDown(){
        this.x += 1;
        this.y += 1;
    }

}