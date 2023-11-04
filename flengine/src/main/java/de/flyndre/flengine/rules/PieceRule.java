package de.flyndre.flengine.rules;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Field;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Piece;
import de.flyndre.flengine.datamodel.enums.Color;
import de.flyndre.flengine.datamodel.enums.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PieceRule {
    
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
        int line = field.getLine().ordinal();
        int row = field.getRow().ordinal();

        switch (board.getPiece(field).getTypeOfFigure()) {
            case PAWN -> moves = getLegalMovesPawn(board.getPieces(), line, row);
            case ROOK -> moves = getLegalMovesRook(board.getPieces(), line, row);
            case KNIGHT -> moves = getLegalMovesKnight(board.getPieces(), line, row);
            case BISHOP -> moves = getLegalMovesBishop(board.getPieces(), line, row);
            case QUEEN -> moves = getLegalMovesQueen(board.getPieces(), line, row);
            case KING -> moves = getLegalMovesKing(board.getPieces(), line, row);
            default -> throw new IllegalArgumentException(String.format(
                "Couldn't read field %s with piece of type %s", field, board.getPiece(field).getTypeOfFigure()
            ));
        }
        return moves;
    }

    /**
     * Returns all possible moves of a pawn at given position.
     * @param pieces chess piece array
     * @param line line index of pawn
     * @param row row index of pawn
     * @return list of possible pawn moves
     */
    private List<Move> getLegalMovesPawn(Piece[][] pieces, int line, int row){

        List<Move> moves = new ArrayList<>();
        Color color = pieces[line][row].getColor();

        int direction = -1;
        if (color.equals(Color.WHITE)) direction = 1;

        Type type = (line == 6 && color.equals(Color.WHITE) || line == 1 && color.equals(Color.BLACK)) ? Type.QUEEN : null;

        // the field straight ahead has to be unoccupied to move
        if (pieces[line+direction][row] == null) {
            moves.add(new Move(BoardUtil.toField(line, row), BoardUtil.toField(line+direction, row), type));
        }
        // the fields diagonal have to be occupied by opponent
        if (row > 0 && pieces[line+direction][row-1] != null && !pieces[line+direction][row-1].getColor().equals(color)) {
            moves.add(new Move(BoardUtil.toField(line, row), BoardUtil.toField(line+direction, row-1), type));
        }
        if (row < 7 && pieces[line+direction][row+1] != null && !pieces[line+direction][row+1].getColor().equals(color)) {
            moves.add(new Move(BoardUtil.toField(line, row), BoardUtil.toField(line+direction, row+1), type));
        }
        // the two fields ahead of the start line have to be unoccupied
        if ((line == 1 && color.equals(Color.WHITE) || line == 6 && color.equals(Color.BLACK)) &&
                    pieces[line+direction][row] == null && pieces[line+2*direction][row] == null) {
            moves.add(new Move(BoardUtil.toField(line, row), BoardUtil.toField(line+2*direction, row)));
        }

        // TODO en passant implementieren

        return moves;
    }

    /**
     * Returns all possible moves of a rook at given position.
     * @param pieces chess piece array
     * @param line line index of rook
     * @param row row index of rook
     * @return list of possible rook moves
     */
    private List<Move> getLegalMovesRook(Piece[][] pieces, int line, int row){

        List<Move> moves = new ArrayList<>();
        int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};

        for (int[] direction : directions) {

            int l = line;
            int r = row;
            while (l + direction[0] >= 0 && l + direction[0] < 8 && r + direction[1] >= 0 && r + direction[1] < 8) {

                // next field in direction is unoccupied
                if (pieces[l + direction[0]][r + direction[1]] == null) {
                    l += direction[0];
                    r += direction[1];
                    moves.add(new Move(BoardUtil.toField(line, row), BoardUtil.toField(l, r)));
                }
                // next field in direction is occupied by opponent
                else if (!pieces[l + direction[0]][r + direction[1]].getColor().equals(pieces[line][row])) {
                    moves.add(new Move(BoardUtil.toField(line, row), BoardUtil.toField(l + direction[0], r + direction[1])));
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
     * @param pieces chess piece array
     * @param line line index of knight
     * @param row row index of knight
     * @return list of possible knight moves
     */
    private List<Move> getLegalMovesKnight(Piece[][] pieces, int line, int row){

        List<Move> moves = new ArrayList<>();
        int[][] directions = {{-2,-1},{-2,1},{2,-1},{2,1},{-1,-2},{-1,2},{1,-2},{1,2}};

        for (int[] direction : directions) {

            // field has to be unoccupied or occupied by an opponent
            if (line + direction[0] >= 0 && line + direction[0] < 8 && row + direction[1] >= 0 && row + direction[1] < 8 &&
                    (pieces[line + direction[0]][row + direction[1]] == null ||
                    !pieces[line + direction[0]][row + direction[1]].getColor().equals(pieces[line][row].getColor()))) {
                moves.add(new Move(BoardUtil.toField(line, row), BoardUtil.toField(line + direction[0], row + direction[1])));
            }
        }
        return moves;
    }

    /**
     * Returns all possible moves of a bishop at given position.
     * @param pieces chess piece array
     * @param line line index of bishop
     * @param row row index of bishop
     * @return list of possible bishop moves
     */
    private List<Move> getLegalMovesBishop(Piece[][] pieces, int line, int row){

        List<Move> moves = new ArrayList<>();
        int[][] directions = {{1,1},{-1,-1},{1,-1},{-1,1}};

        for (int[] direction : directions) {

            int l = line;
            int r = row;
            while (l + direction[0] >= 0 && l + direction[0] < 8 && r + direction[1] >= 0 && r + direction[1] < 8) {

                // next field in direction is unoccupied
                if (pieces[l + direction[0]][r + direction[1]] == null) {
                    l += direction[0];
                    r += direction[1];
                    moves.add(new Move(BoardUtil.toField(line, row), BoardUtil.toField(l, r)));
                }
                // next field in direction is occupied by opponent
                else if (!pieces[l + direction[0]][r + direction[1]].getColor().equals(pieces[line][row])) {
                    moves.add(new Move(BoardUtil.toField(line, row), BoardUtil.toField(l + direction[0], r + direction[1])));
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
     * @param pieces chess piece array
     * @param line line index of queen
     * @param row row index of queen
     * @return list of possible queen moves
     */
    private List<Move> getLegalMovesQueen(Piece[][] pieces, int line, int row){
        return Stream.concat(
            getLegalMovesBishop(pieces, line, row).stream(), getLegalMovesRook(pieces, line, row).stream()
        ).toList();
    }

    /**
     * Returns all possible moves of a king at given position.
     * @param pieces chess piece array
     * @param line line index of king
     * @param row row index of king
     * @return list of possible king moves
     */
    private List<Move> getLegalMovesKing(Piece[][] pieces, int line, int row){

        List<Move> moves = new ArrayList<>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                if (i == 0 && j == 0) continue;

                // TODO steht der König im Schach, muss der nächste Zug das ändern

                // TODO der König darf sich nicht auf ein gedecktes Feld bewegen

                // field has to be unoccupied or occupied by an opponent
                if (line+i >= 0 && line+i < 8 && row+j >= 0 && row+j < 8 &&
                        pieces[line+i][row+j] == null || pieces[line+i][row+j].getColor().equals(pieces[line][row].getColor())) {
                    moves.add(new Move(BoardUtil.toField(line, row), BoardUtil.toField(line+i, row+j)));
                }
            }
        }
        return moves;
    }
}
