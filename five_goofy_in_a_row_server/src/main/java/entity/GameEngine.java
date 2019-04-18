package entity;

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

    public boolean putPiece(String id, Position p) {
        boolean res = this.board.putPiece(p,getColor(id));
        if(res) {
            curPlayer = curPlayer.equals(id1)? id2: id1;
        }
        return res;
    }

    private int getColor(String id) {
        if(id1.equals(id)) {
            return 1;
        }else if(id2.equals(id)) {
            return 2;
        }else {
            return 0;
        }
    }

    private String checkWinner(Position p) {
        int res = this.board.checkFiveInRow(p);
        if(res == 1) {
            return id1;
        }else if(res == 2) {
            return id2;
        }else {
            return null;
        }
    }

    public String readyPlayer() {
        return curPlayer;
    }

    public boolean checkDraw() {
        return this.board.checkDraw();
    }
}
