package com.goofyobject.tetris.game.AI;

import com.goofyobject.tetris.game.entity.Board;
import com.goofyobject.tetris.game.entity.Position;

public interface AIStrategy {


    public Position getComputerPosition(Board board);
    // public Position getComputerPosition2(Board board); 
    // public Position getComputerPositionSimple(Board board);
    // public int evaluateScore(Board board, int color);


    // public void setAIStrategy();

}