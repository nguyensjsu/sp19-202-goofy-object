package com.goofyobject.tetris.game.AI;

import com.goofyobject.tetris.game.entity.Board;
import com.goofyobject.tetris.game.entity.Position;

public interface AIPlayerIService {
    public Position getComputerPosition(Board board);
}