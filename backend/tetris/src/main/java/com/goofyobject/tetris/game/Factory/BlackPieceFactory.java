package com.goofyobject.tetris.game.Factory;

import com.goofyobject.tetris.game.entity.Piece;

public class BlackPieceFactory implements PieceFactory {

    public Piece createPiece() {
        return new Piece(1);
    }
}
