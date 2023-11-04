package de.flyndre.flengine.rules;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Field;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.enums.Color;

import java.util.ArrayList;
import java.util.List;

public class Rule extends PieceRule {

    public List<Move> getLegalMoves(Board board, Color color) { return new ArrayList<>(); }

    public List<Move> getLegalMoves(Board board, Field field) { return new ArrayList<>(); }

    public boolean isLegalMove(Board board, Move move) { return false; }

    public boolean isCheck(Board board, Color color) { return false; }

    public boolean isCheckmate(Board board, Color color) { return false; }

    public boolean isRemis(Board board) { return false; }
}
