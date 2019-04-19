package com.goofyobject.tetris.domain;

public class GameEngine {
    private String p1; // p1-black first hand
    private String p2; // p2-white
    private Board board;

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public GameEngine(String p1, String p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.board = new Board();
    }

    public String getPlayer1() {
        return this.p1;
    }

    public String getPlayer2() {
        return this.p2;
    }

    public String readyPlayer(){
        return this.p2;
    }

    public boolean putPiece(String sessionId, Position position){
        return true;
    }

    public String checkWinner(Position position){
        return this.p1;
    }

    public boolean checkDraw(){
        return false;
    }



}