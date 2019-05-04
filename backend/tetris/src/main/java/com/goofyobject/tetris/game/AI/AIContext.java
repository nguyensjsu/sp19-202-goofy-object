package com.goofyobject.tetris.game.AI;

import com.goofyobject.tetris.game.entity.Board;
import com.goofyobject.tetris.game.entity.Piece;
import com.goofyobject.tetris.game.entity.Position;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.autoconfigure.web.ResourceProperties.Strategy;
import org.springframework.stereotype.Component;

import java.util.Random;

public class AIContext {

    private AIStrategy aiStrategy;

    public void setAIStrategy(AIStrategy aiStrategy){
        this.aiStrategy = aiStrategy;
    }

    public Position operationAI(Board board){
        Position result = aiStrategy.getComputerPosition(board);
        return result;
    }
}