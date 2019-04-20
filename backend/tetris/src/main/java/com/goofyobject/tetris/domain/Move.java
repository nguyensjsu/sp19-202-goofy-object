package com.goofyobject.tetris.domain;

public class Move {
    private String username;
    private int x;
    private int y;

    public Move(String u, int x, int y) {
        this.username = u;
        this.x = x;
        this.y = y;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String user) {
        this.username = user;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
}