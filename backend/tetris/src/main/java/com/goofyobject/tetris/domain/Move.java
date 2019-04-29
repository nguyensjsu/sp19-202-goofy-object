package com.goofyobject.tetris.domain;

import java.util.ArrayList;
import java.util.HashMap;

public class Move extends Reply {
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
    
    @Override
    public void getObj(HashMap<String,Object> objMap) {

        HashMap<String,Object> moveMap = new HashMap<>();
        moveMap.put("username",username);
        moveMap.put("x",x);
        moveMap.put("y",y);

        objMap.put("move",moveMap);

        if (this.getDecorator() != null ){
            this.getDecorator().getObj(objMap);
        }
    }
}