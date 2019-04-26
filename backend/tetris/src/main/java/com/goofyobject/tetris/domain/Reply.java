package com.goofyobject.tetris.domain;

import java.util.ArrayList;
import java.util.HashMap;

class Reply implements Message {

    private Message msgDecorator;

    Message getDecorator() {
        return msgDecorator;
    }

    public void setDecorator(Message msgDecorator) {
        this.msgDecorator = msgDecorator;
    }

    @Override
    public void getObj(HashMap<String,Object> objMap) {
  
    }



 
    
}