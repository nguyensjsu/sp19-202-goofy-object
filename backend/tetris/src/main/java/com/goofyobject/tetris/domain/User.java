package com.goofyobject.tetris.domain;

import java.util.HashMap;

public class User extends Reply {
    private String username;
    private String sessionId;

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


    public String getSessionId() {
        return this.sessionId;
    }
    
	public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
	}

    @Override
    public void getObj(HashMap<String,Object> objMap) {
        objMap.put("username",username);
        if (this.getDecorator() != null ){
            this.getDecorator().getObj(objMap);
        }     
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (o == null || !(o instanceof User)) {
            return false;
        }

        User otherUser = (User) o;

        return otherUser.username.equals(username);
    }

    @Override
    public int hashCode() {
        return 21 + (17 * username.hashCode()) ;
    }

}
