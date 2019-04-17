package entity;

public class GameEngine {
    private Player p1;      //p1-black  first hand
    private Player p2;      //p2-white
    private Board board;

    public GameEngine(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.board = new Board();
    }

    public void putPiece(Position p, int c) {
        this.board.putPiece(p,c);
    }

    public int checkWin(Position p) {
        return this.board.checkFiveInRow(p);
    }

    public void notifyReady(Player palyer) {

    }

    public void notifyEnd(Player palyer, int winner) {

    }

    public void startGame() {
        int winner = 0;
        int color = 1;
        Player cur = p1;
        Position p = new Position();
        while( (winner = checkWin(p)) == 0) {
            notifyReady(cur);
            // p = socket.receive
            putPiece(p,color);
            color = color==1? 2 : 1;
            cur = cur == p1 ? p2 : p1;
        }
        notifyEnd(p1, winner);
        notifyEnd(p1, winner);
    }

}
