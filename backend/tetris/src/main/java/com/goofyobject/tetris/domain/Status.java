package com.goofyobject.tetris.domain;

import java.util.HashMap;


public class Status extends Reply{
    
    private final int c;

    public Status(int c) {
        this.c = c;
    }
    
    @Override
    public void getObj(HashMap<String,Object> objMap) {
        objMap.put("status",c);
        if (this.getDecorator() != null ){
            this.getDecorator().getObj(objMap);
        }     
    }
 
}
