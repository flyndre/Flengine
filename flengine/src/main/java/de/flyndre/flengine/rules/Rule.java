package de.flyndre.flengine.rules;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Field;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.enums.Color;
import de.flyndre.flengine.datamodel.enums.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tobias
 * Implementation of all chess board rules.
 */
public class Rule extends PieceRule {

    private List<Field> checkedFields = new ArrayList<>();
    private List<Field> criticalFields = new ArrayList<>();

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

                if (board.getPiece(new Field(LINES[line], ROWS[row])) != null &&
                        board.getPiece(new Field(LINES[line], ROWS[row])).getColor().equals(color))
                {
                    moves.addAll(getLegalMoves(board, new Field(LINES[line], ROWS[row])));
                }
            }
        }

        if (isChecked(board, color)) {

            Field kingField = null;

            for (int line = 0; line < 8; line++) {
                for (int row = 0; row < 8; row++) {
                    if (board.getPiece(new Field(LINES[line], ROWS[row])) != null &&
                        board.getPiece(new Field(LINES[line], ROWS[row])).getColor().equals(color) &&
                        board.getPiece(new Field(LINES[line], ROWS[row])).getTypeOfFigure().equals(Type.KING))
                    {
                        kingField = new Field(LINES[line], ROWS[row]);
                        break;
                    }
                }
            }

            List<Field> checkedFields = getCheckedFields(board, kingField);
        }

        return moves;
    }

    /**
     * Returns if the given color is in check. <br>
     * A check occurs if the current field of the king is covered by a piece of the opponent.
     * @param board current chess board
     * @param color player color to check
     * @return true if the king is in check
     */
    public boolean isChecked(Board board, Color color) {

        Color opponentColor = color.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;

        for (int line = 0; line < 8; line++) {
            for (int row = 0; row < 8; row++) {

                Field field = new Field(LINES[line], ROWS[row]);

                if (board.getPiece(field) != null && board.getPiece(field).getTypeOfFigure().equals(Type.KING) &&
                        board.getPiece(field).getColor().equals(color))
                {
                    return isFieldCovered(board, field, opponentColor);
                }
            }
        }
        throw new RuntimeException(String.format("King of %s couldn't be found on the board", color));
    }

    /**
     * Returns if the given color is in checkmate. <br>
     * A checkmate occurs if the king can be captured in the current or next move with no possibility to escape. <br>
     * If a king is in checkmate, the game is over.
     * @param board current chess board
     * @param color player color to check
     * @return true if the king is in check
     */
    public boolean isCheckmated(Board board, Color color) {

        return false; // TODO schachmatt implementieren
    }

    /**
     * Returns if the current chess board ends in a draw. <br>
     * A remis occurs when:
     * <ul>
     *     <li>a player is unable to move but is not in check</li>
     *     <li>a checkmate is impossible (king vs king, king vs king & knight, king vs king & bishop)</li>
     *     <li>both players agree on a remis</li>
     *     <li>a few more...</li>
     * </ul>
     *
     * @see <a href="https://de.wikipedia.org/wiki/Remis#Remis_im_Schach">Remis im Schach</a>
     * @see <a href="https://de.wikipedia.org/wiki/Positionelles_Remis">Positionelles Remis</a>
     * @see <a href="https://de.wikipedia.org/wiki/Tote_Stellung">Tote Stellung</a>
     *
     * @param board current chess board
     * @return true if the game ends in a draw
     */
    public boolean isRemis(Board board) {

        // TODO weitere remis-regeln implementieren

        return (getLegalMoves(board, Color.WHITE).isEmpty() || getLegalMoves(board, Color.BLACK).isEmpty());
    }

    private List<Field> getCheckedFields(Board board, Field field) {

        return new ArrayList<>();
    }
}
