package five_in_a_row.statemachine;

import five_in_a_row.entity.Board;
import five_in_a_row.entity.Position;

public class PlayerTwoMoveState implements GameState {
    private GameEngine engine;
    private String playerId;
    private Board board;

    public PlayerTwoMoveState(GameEngine engine, String id, Board board) {
        this.engine = engine;
        this.playerId = id;
        this.board = board;
    }

    @Override
    public boolean putPiece(String id, Position p) {
        if(!playerId.equals(id)) {return false;}
        boolean res = this.board.putPiece(p,2);
        if(res) {
            this.engine.switchToPlayerOneMoveState();
            return true;
        }
        return false;
    }


    @Override
    public String readyPlayer() {
        return playerId;
    }

    public String getPlayerId() {
        return playerId;
    }
}
