package com.goofyobject.tetris.game.AI;

import com.goofyobject.tetris.game.entity.Board;
import com.goofyobject.tetris.game.entity.Piece;
import com.goofyobject.tetris.game.entity.Position;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import java.util.Random;

public class AIEasy implements AIStrategy {

    public Position getComputerPosition(Board board){

        Random rn = new Random();
        int x = rn.nextInt(14);
        int y = rn.nextInt(14);
        while (board.getGrid()[x][y] != null) {
            x = rn.nextInt(14);
            y = rn.nextInt(14);
        }
        Position result = new Position(x, y);
        return result;
    }
}
