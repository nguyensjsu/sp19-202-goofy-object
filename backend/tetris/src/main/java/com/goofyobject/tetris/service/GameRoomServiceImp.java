package com.goofyobject.tetris.service;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class GameRoomServiceImp implements GameRoomService {

    private final ConcurrentHashMap<String, Integer> room = new ConcurrentHashMap<>();

    @Override
    public boolean addSession(String sessionId) {

        if (room.contains(sessionId)) {
            return false;
        }

        this.room.put(sessionId, 1);
        return true;
    }

    @Override
    public boolean removeSession(String sessionId) {

        if (room.containsKey(sessionId)) {
            room.remove(sessionId);
            return true;
        }
        return false;
    }

    @Override
    public Enumeration<String> getRoomSessions() {
        return room.keys();
    }

}