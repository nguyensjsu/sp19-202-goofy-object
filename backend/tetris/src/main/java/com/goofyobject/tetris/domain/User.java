package com.goofyobject.tetris.domain;

import java.util.ArrayList;
import java.util.HashMap;

public class User extends Reply {
    private String username;

    public User(){
        
    }
    public User(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void getObj(HashMap<String,Object> objMap) {
        objMap.put("username",username);
        if (this.getDecorator() != null ){
            this.getDecorator().getObj(objMap);
        }     
    }

}
