package com.goofyobject.tetris.game.Factory;

import com.goofyobject.tetris.game.entity.Piece;

public abstract class AbstractPieceFactory {

    public static PieceFactory getBlackPieceFactory() {
        return (PieceFactory) new BlackPieceFactory();
    }

    public static PieceFactory getWhitePieceFactory() {
        return (PieceFactory) new WhitePieceFactory();
    }
}
