package com.goofyobject.tetris.domain;

public class Game {
    private Player p1; // p1-black first hand
    private Player p2; // p2-white
    private Board board;

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Game(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.board = new Board();
    }

    public Player getPlayer1() {
        return this.p1;
    }

    public Player getPlayer2() {
        return this.p2;
    }

    public void setPlayer2(Player player) {
        this.p2 = player;
    }
}