package de.flyndre.flengine.rules;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Field;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.enums.Color;
import de.flyndre.flengine.datamodel.enums.Line;
import de.flyndre.flengine.datamodel.enums.Row;

import java.util.ArrayList;
import java.util.List;

public class Rule extends PieceRule {

    private final Line[] LINES = Line.values();
    private final Row[] ROWS = Row.values();

    public List<Move> getLegalMoves(Board board, Color color) {

        List<Move> moves = new ArrayList<>();

        for (int line = 0; line < 8; line++) {
            for (int row = 0; row < 8; row++) {

                if (board.getPiece(new Field(LINES[line], ROWS[row])) != null &&
                        board.getPiece(new Field(LINES[line], ROWS[row])).getColor().equals(color))
                {
                    moves.addAll(getLegalMoves(board, new Field(LINES[line], ROWS[row])));
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
