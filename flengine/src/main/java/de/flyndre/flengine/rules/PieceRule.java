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
                "Couldn't read piece type %s", board.getPiece(field).getTypeOfFigure()
            ));
        }
        return moves;
    }

    private List<Move> getLegalMovesPawn(Piece[][] pieces, int line, int row){

        List<Move> moves = new ArrayList<>();
        Color color = pieces[line][row].getColor();

        int direction = -1;
        if (color.equals(Color.WHITE)) direction = 1;

        Type type = (line == 6 && color.equals(Color.WHITE) || line == 1 && color.equals(Color.BLACK)) ? Type.QUEEN : null;

        // the field straight ahead has to be unoccupied to move
        if (pieces[line+direction][row] == null) {
            moves.add(new Move(new Field(line, row), new Field(line+direction, row), type));
        }
        // the fields diagonal have to be occupied by opponent
        if (row > 0 && pieces[line+direction][row-1] != null && !pieces[line+direction][row-1].getColor().equals(color)) {
            moves.add(new Move(new Field(line, row), new Field(line+direction, row-1), type));
        }
        if (row < 7 && pieces[line+direction][row+1] != null && !pieces[line+direction][row+1].getColor().equals(color)) {
            moves.add(new Move(new Field(line, row), new Field(line+direction, row+1), type));
        }
        // the two fields ahead of the start line have to be unoccupied
        if ((line == 1 && color.equals(Color.WHITE) || line == 6 && color.equals(Color.BLACK)) &&
                    pieces[line+direction][row] == null && pieces[line+2*direction][row] == null) {
            moves.add(new Move(new Field(line, row), new Field(line+2*direction, row)));
        }

        // TODO en passant implementieren

        return moves;
    }


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
                    moves.add(new Move(new Field(line, row), new Field(l, r)));
                }
                // next field in direction is occupied by opponent
                else if (!pieces[l + direction[0]][r + direction[1]].getColor().equals(pieces[line][row])) {
                    moves.add(new Move(new Field(line, row), new Field(l + direction[0], r + direction[1])));
                    break;
                }
                // next field in direction is occupied by own piece
                else break;
            }
        }
        return moves;
    }

    private List<Move> getLegalMovesKnight(Piece[][] pieces, int line, int row){
        return new ArrayList<>();
    }

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
                    moves.add(new Move(new Field(line, row), new Field(l, r)));
                }
                // next field in direction is occupied by opponent
                else if (!pieces[l + direction[0]][r + direction[1]].getColor().equals(pieces[line][row])) {
                    moves.add(new Move(new Field(line, row), new Field(l + direction[0], r + direction[1])));
                    break;
                }
                // next field in direction is occupied by own piece
                else break;
            }
        }
        return moves;
    }

    private List<Move> getLegalMovesQueen(Piece[][] pieces, int line, int row){
        return Stream.concat(
            getLegalMovesBishop(pieces, line, row).stream(), getLegalMovesRook(pieces, line, row).stream()
        ).toList();
    }

    private List<Move> getLegalMovesKing(Piece[][] pieces, int line, int row){
        return new ArrayList<>();
    }
}
