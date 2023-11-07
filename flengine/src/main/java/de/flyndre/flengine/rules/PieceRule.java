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
import java.util.stream.Stream;

public class PieceRule {

    private final Line[] LINES = Line.values();
    private final Row[] ROWS = Row.values();

    // TODO was soll diese methode machen??
    public List<Move> getLegalMoves(Board board){
        return new ArrayList<>();
    }

    /**
     * Returns all possible moves of a piece on the given field.
     * @param board current chessboard
     * @param field given field of piece
     * @return list of possible moves
     */
    public List<Move> getLegalMoves(Board board, Field field) {

        List<Move> moves;

        switch (board.getPiece(field).getTypeOfFigure()) {
            case PAWN -> moves = getLegalMovesPawn(board, field);
            case ROOK -> moves = getLegalMovesRook(board, field);
            case KNIGHT -> moves = getLegalMovesKnight(board, field);
            case BISHOP -> moves = getLegalMovesBishop(board, field);
            case QUEEN -> moves = getLegalMovesQueen(board, field);
            case KING -> moves = getLegalMovesKing(board, field);
            default -> throw new IllegalArgumentException(String.format(
                "Couldn't read field %s with piece of type %s", field, board.getPiece(field).getTypeOfFigure()
            ));
        }
        return moves;
    }

    /**
     * Returns all possible moves of a pawn at given position.
     * @param board current chess board
     * @param field current field of a pawn
     * @return list of possible pawn moves
     */
    private List<Move> getLegalMovesPawn(Board board, Field field) {

        List<Move> moves = new ArrayList<>();
        Color color = board.getPiece(field).getColor();
        int fieldLine = field.getLine().ordinal();
        int fieldRow = field.getRow().ordinal();

        int direction = -1;
        if (color.equals(Color.WHITE)) direction = 1;

        // type is only set if pawn moves from second to last line to last line
        Type type = (field.getLine() == Line.SEVEN && color.equals(Color.WHITE) ||
            field.getLine() == Line.TWO && color.equals(Color.BLACK)) ? Type.QUEEN : null;

        // the field straight ahead has to be unoccupied to move
        if (board.getPiece(new Field(LINES[fieldLine + direction], ROWS[fieldRow])) == null)
        {
            moves.add(new Move(
                new Field(LINES[fieldLine], ROWS[fieldRow]),
                new Field(LINES[fieldLine + direction], ROWS[fieldRow]), type));
        }
        // the fields diagonal have to be occupied by opponent
        if (fieldRow > 0 &&
                board.getPiece(new Field(LINES[fieldLine + direction], ROWS[fieldRow - 1])) != null &&
                !board.getPiece(new Field(LINES[fieldLine + direction], ROWS[fieldRow - 1])).getColor().equals(color))
        {
            moves.add(new Move(
                new Field(LINES[fieldLine], ROWS[fieldRow]),
                new Field(LINES[fieldLine + direction], ROWS[fieldRow - 1]), type));
        }
        if (fieldRow < 7 &&
                board.getPiece(new Field(LINES[fieldLine + direction], ROWS[fieldRow + 1])) != null &&
                !board.getPiece(new Field(LINES[fieldLine + direction], ROWS[fieldRow + 1])).getColor().equals(color))
        {
            moves.add(new Move(
                new Field(LINES[fieldLine], ROWS[fieldRow]),
                new Field(LINES[fieldLine + direction], ROWS[fieldRow + 1]), type));
        }
        // the two fields ahead of the start line have to be unoccupied
        if ((field.getLine() == Line.TWO && color.equals(Color.WHITE) || field.getLine() == Line.SEVEN && color.equals(Color.BLACK)) &&
                board.getPiece(new Field(LINES[fieldLine + direction], ROWS[fieldRow])) == null &&
                board.getPiece(new Field(LINES[fieldLine + 2*direction], ROWS[fieldRow])) == null)
        {
            moves.add(new Move(
                new Field(LINES[fieldLine], ROWS[fieldRow]),
                new Field(LINES[fieldLine + 2*direction], ROWS[fieldRow]), type));
        }

        // TODO en passant implementieren

        return moves;
    }

    /**
     * Returns all possible moves of a rook at given position.
     * @param board current chess board
     * @param field current field of a rook
     * @return list of possible rook moves
     */
    private List<Move> getLegalMovesRook(Board board, Field field) {

        List<Move> moves = new ArrayList<>();
        int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};
        int fieldLine = field.getLine().ordinal();
        int fieldRow = field.getRow().ordinal();

        for (int[] direction : directions) {

            int l = fieldLine;
            int r = fieldRow;
            while (l + direction[0] >= 0 && l + direction[0] < 8 && r + direction[1] >= 0 && r + direction[1] < 8)
            {
                // next field in direction is unoccupied
                if (board.getPiece(new Field(LINES[l + direction[0]], ROWS[r + direction[1]])) == null)
                {
                    l += direction[0];
                    r += direction[1];
                    moves.add(new Move(
                        new Field(LINES[fieldLine], ROWS[fieldRow]), new Field(LINES[l], ROWS[r])));
                }
                // next field in direction is occupied by opponent
                else if (!board.getPiece(new Field(LINES[l + direction[0]], ROWS[r + direction[1]])).getColor().equals(board.getPiece(field).getColor()))
                {
                    moves.add(new Move(
                        new Field(LINES[fieldLine], ROWS[fieldRow]), new Field(LINES[l + direction[0]], ROWS[r + direction[1]])));
                    break;
                }
                // next field in direction is occupied by own piece
                else break;
            }
        }
        return moves;
    }

    /**
     * Returns all possible moves of a knight at given position.
     * @param board current chess board
     * @param field current field of a knight
     * @return list of possible knight moves
     */
    private List<Move> getLegalMovesKnight(Board board, Field field) {

        List<Move> moves = new ArrayList<>();
        int[][] directions = {{-2,-1},{-2,1},{2,-1},{2,1},{-1,-2},{-1,2},{1,-2},{1,2}};
        int fieldLine = field.getLine().ordinal();
        int fieldRow = field.getRow().ordinal();

        for (int[] direction : directions) {

            // field has to be unoccupied or occupied by an opponent
            if (fieldLine + direction[0] >= 0 && fieldLine + direction[0] < 8 &&
                    fieldRow + direction[1] >= 0 && fieldRow + direction[1] < 8 &&
                    (board.getPiece(new Field(LINES[fieldLine + direction[0]], ROWS[fieldRow + direction[1]])) == null ||
                    !board.getPiece(new Field(LINES[fieldLine + direction[0]], ROWS[fieldRow + direction[1]])).getColor().equals(board.getPiece(field).getColor())))
            {
                moves.add(new Move(
                    new Field(LINES[fieldLine], ROWS[fieldRow]),
                    new Field(LINES[fieldLine + direction[0]], ROWS[fieldRow + direction[1]])));
            }
        }
        return moves;
    }

    /**
     * Returns all possible moves of a bishop at given position.
     * @param board current chess board
     * @param field current field of a bishop
     * @return list of possible bishop moves
     */
    private List<Move> getLegalMovesBishop(Board board, Field field) {

        List<Move> moves = new ArrayList<>();
        int[][] directions = {{1,1},{-1,-1},{1,-1},{-1,1}};
        int fieldLine = field.getLine().ordinal();
        int fieldRow = field.getRow().ordinal();

        for (int[] direction : directions) {

            int l = fieldLine;
            int r = fieldRow;
            while (l + direction[0] >= 0 && l + direction[0] < 8 && r + direction[1] >= 0 && r + direction[1] < 8)
            {
                // next field in direction is unoccupied
                if (board.getPiece(new Field(LINES[l + direction[0]], ROWS[r + direction[1]])) == null)
                {
                    l += direction[0];
                    r += direction[1];
                    moves.add(new Move(new Field(LINES[fieldLine], ROWS[fieldRow]), new Field(LINES[l], ROWS[r])));
                }
                // next field in direction is occupied by opponent
                else if (!board.getPiece(new Field(LINES[l + direction[0]], ROWS[r + direction[1]])).getColor().equals(board.getPiece(field).getColor()))
                {
                    moves.add(new Move(
                        new Field(LINES[fieldLine], ROWS[fieldRow]),
                        new Field(LINES[l + direction[0]], ROWS[r + direction[1]])));
                    break;
                }
                // next field in direction is occupied by own piece
                else break;
            }
        }
        return moves;
    }

    /**
     * Returns all possible moves of a queen at given position.
     * @param board current chess board
     * @param field current field of a queen
     * @return list of possible queen moves
     */
    private List<Move> getLegalMovesQueen(Board board, Field field) {
        return Stream.concat(
            getLegalMovesBishop(board, field).stream(), getLegalMovesRook(board, field).stream()
        ).toList();
    }

    /**
     * Returns all possible moves of a king at given position.
     * @param board current chess board
     * @param field current field of a king
     * @return list of possible king moves
     */
    private List<Move> getLegalMovesKing(Board board, Field field) {

        List<Move> moves = new ArrayList<>();
        int fieldLine = field.getLine().ordinal();
        int fieldRow = field.getRow().ordinal();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                if (i == 0 && j == 0) continue;

                // TODO steht der König im Schach, muss der nächste Zug das ändern

                // TODO der König darf sich nicht auf ein gedecktes Feld bewegen

                // field has to be unoccupied or occupied by an opponent
                if (fieldLine + i >= 0 && fieldLine + i < 8 && fieldRow + j >= 0 && fieldRow + j < 8 &&
                        (board.getPiece(new Field(LINES[fieldLine + i], ROWS[fieldRow + j])) == null ||
                        !board.getPiece(new Field(LINES[fieldLine + i], ROWS[fieldRow + j])).getColor().equals(board.getPiece(field).getColor())))
                {
                    moves.add(new Move(
                        new Field(LINES[fieldLine], ROWS[fieldRow]), new Field(LINES[fieldLine + i], ROWS[fieldRow + j])));
                }
            }
        }
        return moves;
    }
}
