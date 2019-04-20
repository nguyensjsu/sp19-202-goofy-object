package com.goofyobject.tetris.domain;


public enum Status {
    OK(202), WIN(211), LOSE(212), DRAW(213), Black(220), White(230), FAIL(400);

    private final int v;

    private Status(int v) {
        this.v = v;
    }

    public int getValue() {
        return v;
    }
}
