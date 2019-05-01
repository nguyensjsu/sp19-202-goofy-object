package com.goofyobject.tetris.game.Factory;

import com.goofyobject.tetris.game.entity.Piece;

public class PieceFactory {

    private PieceFactory() {}

    public static Piece getNewPiece(int color) {
        return new Piece(color);
    }
}
