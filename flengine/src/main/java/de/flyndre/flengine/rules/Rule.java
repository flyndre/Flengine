package de.flyndre.flengine.rules;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Field;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.enums.Color;
import de.flyndre.flengine.datamodel.enums.Line;
import de.flyndre.flengine.datamodel.enums.Row;
import de.flyndre.flengine.datamodel.enums.Type;

import java.util.ArrayList;
import java.util.List;

public class Rule extends PieceRule {

    private final Line[] LINES = Line.values();
    private final Row[] ROWS = Row.values();

    /**
     * Returns all possible moves of a given color.
     * @param board current chess board
     * @param color piece color
     * @return list of all possible moves
     */
    public List<Move> getLegalMoves(Board board, Color color) {

        List<Move> moves = new ArrayList<>();

        for (int line = 0; line < 8; line++) {
            for (int row = 0; row < 8; row++) {
                if (board.getPiece(new Field(LINES[line], ROWS[row])).getColor().equals(color)) {
                    moves.addAll(getLegalMoves(board, new Field(LINES[line], ROWS[row])));
                }
            }
        }
        return moves;
    }

    public boolean isLegalMove(Board board, Move move) { return false; }

    public boolean isChecked(Board board, Color color) {

        Color opponentColor = color.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;

        for (int line = 0; line < 8; line++) {
            for (int row = 0; row < 8; row++) {

                Field field = new Field(LINES[line], ROWS[row]);

                if (board.getPiece(field).getTypeOfFigure().equals(Type.KING) &&
                        board.getPiece(field).getColor().equals(color))
                {
                    return isFieldCovered(board, field, opponentColor);
                }
            }
        }
        throw new RuntimeException(String.format("King of %s couldn't be found on the board", color));
    }

    public boolean isCheckmated(Board board, Color color) { return false; }

    public boolean isRemis(Board board) { return false; }
}
