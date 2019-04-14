package com.goofyobject.tetris.service;

import java.util.Enumeration;

public interface GameRoomService {

    public boolean addSession(String sessionId);

    public boolean removeSession(String sessionId);

    public Enumeration<String> getRoomSessions();

}