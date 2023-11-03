package org.flyndre.Rules;

import org.flyndre.DataModel.Board;
import org.flyndre.DataModel.Field;
import org.flyndre.DataModel.Move;

import java.util.ArrayList;
import java.util.List;

public class PieceRule {

    public List<Move> getLegalMoves(Board board){
        return new ArrayList<>();
    }

    private List<Move> getLegalMovesPawn(Board board, Field Position){
        return new ArrayList<>();
    }

    private List<Move> getLegalMovesBishop(Board board, Field Position){
        return new ArrayList<>();
    }

    private List<Move> getLegalMovesRook(Board board, Field Position){
        return new ArrayList<>();
    }

    private List<Move> getLegalMovesQueen(Board board, Field Position){
        return new ArrayList<>();
    }

    private List<Move> getLegalMovesKing(Board board, Field Position){
        return new ArrayList<>();
    }

    private List<Move> getLegalMovesKnight(Board board, Field Position){
        return new ArrayList<>();
    }
}
