package com.goofyobject.tetris.game.FactoryPiece;

public class PieceFactory {

    public static Piece getNewPiece(int color) {
        return new Piece(color);
    }
}
