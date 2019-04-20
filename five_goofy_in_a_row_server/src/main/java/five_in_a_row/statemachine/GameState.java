package five_in_a_row.statemachine;

import five_in_a_row.entity.Position;

public interface GameState {

    public boolean putPiece(String id, Position p);

    public String readyPlayer();

    public String getPlayerId();
}
