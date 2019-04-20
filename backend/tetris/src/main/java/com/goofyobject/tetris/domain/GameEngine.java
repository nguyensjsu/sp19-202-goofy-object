package com.goofyobject.tetris.domain;

public class GameEngine {
    private String id1;    //p1-black  first hand
    private String id2;   //p2-white
    private String curPlayer;
    private Position curPosition;
    private Board board;

    public GameEngine(String id1, String id2) {
        this.id1 = id1;
        this.id2 = id2;
        curPlayer = id1;
        this.board = new Board();
    }

    public synchronized boolean putPiece(String id, Position p) {
		if(!curPlayer.equals(id)) {	return false;}
        boolean res = this.board.putPiece(p,getColor(id));
        if(res) {
            curPlayer = curPlayer.equals(id1)? id2: id1;
        }
        return res;
    }

    private synchronized int getColor(String id) {
        if(id1.equals(id)) {
            return 1;
        }else if(id2.equals(id)) {
            return 2;
        }else {
            return 0;
        }
    }

    public synchronized String checkWinner(Position p) {
        int res = this.board.checkFiveInRow(p);
        if(res == 1) {
            return id1;
        }else if(res == 2) {
            return id2;
        }else {
            return null;
        }
    }

    public synchronized String readyPlayer() {
        return curPlayer;
    }

    public synchronized boolean checkDraw() {
        return this.board.checkDraw();
    }

	public String getId1() {
		return this.id1;
	}

	public void setId1(String id1) {
		this.id1 = id1;
    }
    
    public String getId2() {
		return this.id2;
	}

	public void setId2(String id2) {
		this.id2 = id2;
	}
}
