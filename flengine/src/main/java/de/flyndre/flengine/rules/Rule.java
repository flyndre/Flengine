package de.flyndre.flengine.rules;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Field;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.enums.Color;

import java.util.ArrayList;
import java.util.List;

public class Rule extends PieceRule {

    public List<Move> getLegalMoves(Board board, Color color) {

        List<Move> moves = new ArrayList<>();

        for (int line = 0; line < 8; line++) {
            for (int row = 0; row < 8; row++) {
                if (board.getPieces()[line][row].getColor().equals(color)) {
                    moves.addAll(getLegalMoves(board, BoardUtil.toField(line, row)));
                }
            }
        }
        return moves;
    }

    public boolean isLegalMove(Board board, Move move) { return false; }

    public boolean isCheck(Board board, Color color) { return false; }

    public boolean isCheckmate(Board board, Color color) { return false; }

    public boolean isRemis(Board board) { return false; }
}
