package de.flyndre.flengine.rules;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Field;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.enums.Color;
import de.flyndre.flengine.datamodel.enums.Line;
import de.flyndre.flengine.datamodel.enums.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tobias
 * Implementation of all chess board rules.
 */
public class Rule extends PieceRule {

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

        List<Field> checkedFields = getCheckedFields(board, kingField);
        Map<Field, List<Field>> pinnedFields = getPinnedFields(board, kingField);

        // if there are pinned or checked fields, not all available moves are legal
        // filter out all moves that are in conflict with pinnedFields or checkedFields
        if (!pinnedFields.isEmpty() || !checkedFields.isEmpty()) {

            List<Move> allMoves = new ArrayList<>(moves);
            moves.clear();

            for (Move move : allMoves) {
                // if fromField of field is in pinnedFields hashmap
                if (pinnedFields.containsKey(move.getFrom())) {
                    // if toField is in the pinnedFields hashmap, checkedFields are empty or contain toField
                    if (pinnedFields.get(move.getFrom()).contains(move.getTo()) && (checkedFields.isEmpty() ||
                            checkedFields.contains(move.getTo()))) {
                        moves.add(move);
                    }
                }
                // if unchecked, or toField is field in checkedFields list and king can move
                else if (checkedFields.isEmpty() || checkedFields.contains(move.getTo()) || move.getFrom().equals(kingField))
                {
                    moves.add(move);
                }
            }
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

    /**
     * Returns if the given move is legal.
     * @param board current chess board
     * @param move move to check
     * @return true if the given move is legal
     */
    public boolean isLegalMove(Board board, Move move) {

        // move object contains null attributes
        if (move.getFrom() == null || move.getTo() == null || move.getFrom().getLine() == null ||
                move.getFrom().getRow() == null || move.getTo().getLine() == null || move.getTo().getRow() == null)
        {
            return false;
        }

        // from field is empty / has no type
        if (board.getPiece(move.getFrom()) == null || board.getPiece(move.getFrom()).getTypeOfFigure() == null) return false;

        // from & to field are the same color
        if (board.getPiece(move.getFrom()).getColor().equals(board.getPiece(move.getTo()).getColor())) return false;

        // pawn move from the second to last line has to be promoted
        if (board.getPiece(move.getFrom()).getTypeOfFigure().equals(Type.PAWN) &&
                (board.getPiece(move.getFrom()).getColor().equals(Color.WHITE) && move.getFrom().getLine().equals(Line.SEVEN) ||
                board.getPiece(move.getFrom()).getColor().equals(Color.BLACK) && move.getFrom().getLine().equals(Line.TWO)))
        {
            if (move.getChangeTo() == null) return false;
        }
        else if (move.getChangeTo() != null) return false;

        // move is not in list of legal moves
        return getLegalMoves(board, move.getFrom()).contains(move);
    }

    /**
     * Returns a list of all possible fields that pieces are able to move when king is in check. <br>
     * Can contain a single field (if checked by knight or pawn) or a list of fields (if checked by rook, bishop, queen).
     * @param board current chess board
     * @param kingField field of the king
     * @return list of possible fields to move to
     */
    private List<Field> getCheckedFields(Board board, Field kingField) {

        List<Field> checkedFields = new ArrayList<>();
        Color opponentColor = board.getPiece(kingField).getColor().equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
        int[][] rookDirections = {{0,1},{0,-1},{1,0},{-1,0}};
        int[][] bishopDirections = {{1,1},{-1,-1},{1,-1},{-1,1}};
        int[][] knightMoves = {{-2,-1},{-2,1},{2,-1},{2,1},{-1,-2},{-1,2},{1,-2},{1,2}};
        int pawnDirection = opponentColor.equals(Color.WHITE) ? -1 : 1;

        int fieldLine = kingField.getLine().ordinal();
        int fieldRow = kingField.getRow().ordinal();

        // field is covered by pawn
        if (opponentColor.equals(Color.WHITE) && fieldLine > 0 || opponentColor.equals(Color.BLACK) && fieldLine < 7) {
            if (fieldRow > 0 && board.getPiece(new Field(LINES[fieldLine + pawnDirection], ROWS[fieldRow - 1])) != null &&
                board.getPiece(new Field(LINES[fieldLine + pawnDirection], ROWS[fieldRow - 1])).getTypeOfFigure().equals(Type.PAWN) &&
                board.getPiece(new Field(LINES[fieldLine + pawnDirection], ROWS[fieldRow - 1])).getColor().equals(opponentColor))
            {
                checkedFields.add(new Field(LINES[fieldLine + pawnDirection], ROWS[fieldRow - 1]));
            }
            if (fieldRow < 7 && board.getPiece(new Field(LINES[fieldLine + pawnDirection], ROWS[fieldRow + 1])) != null &&
                board.getPiece(new Field(LINES[fieldLine + pawnDirection], ROWS[fieldRow + 1])).getTypeOfFigure().equals(Type.PAWN) &&
                board.getPiece(new Field(LINES[fieldLine + pawnDirection], ROWS[fieldRow + 1])).getColor().equals(opponentColor))
            {
                checkedFields.add(new Field(LINES[fieldLine + pawnDirection], ROWS[fieldRow + 1]));
            }
        }

        // field is covered by knight
        for (int[] knightMove : knightMoves) {
            if (fieldLine + knightMove[0] >= 0 && fieldLine + knightMove[0] < 8 && fieldRow + knightMove[1] >= 0 && fieldRow + knightMove[1] < 8 &&
                board.getPiece(new Field(LINES[fieldLine + knightMove[0]], ROWS[fieldRow + knightMove[1]])) != null &&
                board.getPiece(new Field(LINES[fieldLine + knightMove[0]], ROWS[fieldRow + knightMove[1]])).getTypeOfFigure().equals(Type.KNIGHT) &&
                board.getPiece(new Field(LINES[fieldLine + knightMove[0]], ROWS[fieldRow + knightMove[1]])).getColor().equals(opponentColor))
            {
                checkedFields.add(new Field(LINES[fieldLine + knightMove[0]], ROWS[fieldRow + knightMove[1]]));
            }
        }

        // field is covered by rook or queen
        for (int[] rookDirection : rookDirections) {

            int l = fieldLine + rookDirection[0];
            int r = fieldRow + rookDirection[1];

            while (l >= 0 && l < 8 && r >= 0 && r < 8)
            {
                if (board.getPiece(new Field(LINES[l], ROWS[r])) != null) {
                    if ((board.getPiece(new Field(LINES[l], ROWS[r])).getTypeOfFigure().equals(Type.ROOK) ||
                        board.getPiece(new Field(LINES[l], ROWS[r])).getTypeOfFigure().equals(Type.QUEEN)) &&
                        board.getPiece(new Field(LINES[l], ROWS[r])).getColor().equals(opponentColor))
                    {
                        // add all fields to checkedFields list
                        checkedFields.add(new Field(LINES[l], ROWS[r]));
                        while (board.getPiece(new Field(LINES[l - rookDirection[0]], ROWS[r - rookDirection[1]])) == null)
                        {
                            l -= rookDirection[0];
                            r -= rookDirection[1];
                            checkedFields.add(new Field(LINES[l], ROWS[r]));
                        }
                    }
                    break;
                }
                l += rookDirection[0];
                r += rookDirection[1];
            }
        }

        // field is covered by bishop or queen
        for (int[] bishopDirection : bishopDirections) {

            int l = fieldLine + bishopDirection[0];
            int r = fieldRow + bishopDirection[1];

            while (l >= 0 && l < 8 && r >= 0 && r < 8)
            {
                if (board.getPiece(new Field(LINES[l], ROWS[r])) != null) {
                    if ((board.getPiece(new Field(LINES[l], ROWS[r])).getTypeOfFigure().equals(Type.BISHOP) ||
                        board.getPiece(new Field(LINES[l], ROWS[r])).getTypeOfFigure().equals(Type.QUEEN)) &&
                        board.getPiece(new Field(LINES[l], ROWS[r])).getColor().equals(opponentColor))
                    {
                        // add all fields to checkedFields list
                        checkedFields.add(new Field(LINES[l], ROWS[r]));
                        while (board.getPiece(new Field(LINES[l - bishopDirection[0]], ROWS[r - bishopDirection[1]])) == null)
                        {
                            l -= bishopDirection[0];
                            r -= bishopDirection[1];
                            checkedFields.add(new Field(LINES[l], ROWS[r]));
                        }
                    }
                    break;
                }
                l += bishopDirection[0];
                r += bishopDirection[1];
            }
        }

        // king cannot attack other king, so no check needed

        return checkedFields;
    }

    /**
     * Returns a hashmap of all pinned fields with a list of corresponding fields they can move to. <br>
     * The list of moveable fields of the pinned piece contains all moves to theoretically move to.
     * It does not consider piece type, so the corresponding field list may contain illegal moves. <br>
     * If the hashmap is empty, no pieces are pinned.
     * @param board current chess board
     * @param kingField field of the king
     * @return hashmap of pinned fields with list of fields to move to
     */
    private Map<Field, List<Field>> getPinnedFields(Board board, Field kingField) {

        Map<Field, List<Field>> pinnedFields = new HashMap<>();
        Color opponentColor = board.getPiece(kingField).getColor().equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
        int[][] rookDirections = {{0,1},{0,-1},{1,0},{-1,0}};
        int[][] bishopDirections = {{1,1},{-1,-1},{1,-1},{-1,1}};

        int fieldLine = kingField.getLine().ordinal();
        int fieldRow = kingField.getRow().ordinal();

        // piece is pinned by rook or queen
        for (int[] rookDirection : rookDirections) {

            int l = fieldLine + rookDirection[0];
            int r = fieldRow + rookDirection[1];
            Field pinnedField = null;

            while (l >= 0 && l < 8 && r >= 0 && r < 8)
            {
                if (board.getPiece(new Field(LINES[l], ROWS[r])) != null) {
                    // found own piece
                    if (!board.getPiece(new Field(LINES[l], ROWS[r])).getColor().equals(opponentColor)) {
                        // already found own piece before
                        if (pinnedField != null) {
                            break;
                        }
                        else pinnedField = new Field(LINES[l], ROWS[r]);
                    }
                    // found opponent rook or queen that pins the own piece
                    else if (pinnedField != null && (board.getPiece(new Field(LINES[l], ROWS[r])).getTypeOfFigure().equals(Type.ROOK) ||
                            board.getPiece(new Field(LINES[l], ROWS[r])).getTypeOfFigure().equals(Type.QUEEN)) &&
                            board.getPiece(new Field(LINES[l], ROWS[r])).getColor().equals(opponentColor))
                    {
                        pinnedFields.put(pinnedField, new ArrayList<>());
                        pinnedFields.get(pinnedField).add(new Field(LINES[l], ROWS[r]));
                        while (!new Field(LINES[l - rookDirection[0]], ROWS[r - rookDirection[1]]).equals(kingField))
                        {
                            l -= rookDirection[0];
                            r -= rookDirection[1];
                            pinnedFields.get(pinnedField).add(new Field(LINES[l], ROWS[r]));
                        }
                        break;
                    }
                }
                l += rookDirection[0];
                r += rookDirection[1];
            }
        }

        // piece is pinned by bishop or queen
        for (int[] bishopDirection : bishopDirections) {

            int l = fieldLine + bishopDirection[0];
            int r = fieldRow + bishopDirection[1];
            Field pinnedField = null;

            while (l >= 0 && l < 8 && r >= 0 && r < 8)
            {
                if (board.getPiece(new Field(LINES[l], ROWS[r])) != null) {
                    // found own piece
                    if (!board.getPiece(new Field(LINES[l], ROWS[r])).getColor().equals(opponentColor)) {
                        // already found own piece before
                        if (pinnedField != null) {
                            break;
                        }
                        else pinnedField = new Field(LINES[l], ROWS[r]);
                    }
                    // found opponent bishop or queen that pins the own piece
                    else if (pinnedField != null && (board.getPiece(new Field(LINES[l], ROWS[r])).getTypeOfFigure().equals(Type.BISHOP) ||
                            board.getPiece(new Field(LINES[l], ROWS[r])).getTypeOfFigure().equals(Type.QUEEN)) &&
                            board.getPiece(new Field(LINES[l], ROWS[r])).getColor().equals(opponentColor))
                    {
                        pinnedFields.put(pinnedField, new ArrayList<>());
                        pinnedFields.get(pinnedField).add(new Field(LINES[l], ROWS[r]));
                        while (!new Field(LINES[l - bishopDirection[0]], ROWS[r - bishopDirection[1]]).equals(kingField))
                        {
                            l -= bishopDirection[0];
                            r -= bishopDirection[1];
                            pinnedFields.get(pinnedField).add(new Field(LINES[l], ROWS[r]));
                        }
                        break;
                    }
                }
                l += bishopDirection[0];
                r += bishopDirection[1];
            }
        }
        return pinnedFields;
    }
}
