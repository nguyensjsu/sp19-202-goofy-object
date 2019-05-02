package com.goofyobject.tetris.game.Factory;

import com.goofyobject.tetris.game.entity.Piece;

public class WhitePieceFactory implements PieceFactory{

    public Piece createPiece() {
            return new Piece(2);
    }
}
