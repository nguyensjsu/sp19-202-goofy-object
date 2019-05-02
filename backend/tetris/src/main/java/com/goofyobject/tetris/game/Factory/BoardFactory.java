package com.goofyobject.tetris.game.Factory;

import com.goofyobject.tetris.game.entity.Board;

public class BoardFactory {

    private BoardFactory() {}
    public static Board createBoard() {
        return new Board();
    }
}
