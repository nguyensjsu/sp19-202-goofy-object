package five_in_a_row.statemachine;

import five_in_a_row.entity.Position;

public class GameEndState implements GameState {

    @Override
    public boolean putPiece(String id, Position p) {
        return false;
    }

    @Override
    public String readyPlayer() {
        return null;
    }

    @Override
    public String getPlayerId() {
        return null;
    }
}
