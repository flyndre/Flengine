package de.flyndre.flengine.datamodel;

import de.flyndre.flengine.datamodel.enums.Color;
import de.flyndre.flengine.datamodel.enums.Line;
import de.flyndre.flengine.datamodel.enums.Row;
import de.flyndre.flengine.datamodel.enums.Type;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Lukas
 * This class represents the chess board in the game logic.
 */
public class Board {
    /**
     * Represents the en passant field.
     * If not existing the value is null.
     */
    private Field enPassantField = null;
    /**
     * Represents the chess board with all figures on it.
     * First line second row
     */
    private Piece[][] pieces = new Piece[8][8];
    /**
     * Stores the color of the next on turn player.
     */
    private Color nextColor = Color.WHITE;

    /**
     * Number of the next move to be done on the board. This is important for fen string support.
     */
    private int moveCounter = 1;
    /**
     * Indicates whether white can do short castling
     */
    private boolean whiteShortCastling = true;
    /**
     * Indicates whether white can do long castling
     */
    private boolean whiteLongCastling = true;
    /**
     * Indicates whether black can do short castling
     */
    private boolean blackShortCastling = true;
    /**
     * Indicates whether black can do long castling
     */
    private boolean blackLongCastling = true;

    /**
     * Executes a Move on the Board. If the target field is occupied it'll replace the piece on the target field with
     * the piece on the source field. It also changes the color of the next player and updates the castling flags
     * accordingly to the played moves. In case of castling both figures involved are moved.
     * @param move the move to be executed.
     * @throws IllegalArgumentException if the from field og the move is empty.
     */
    public void playMove(Move move){
        if (getPiece(move.getFrom())==null){
            throw new IllegalArgumentException(String.format("The from field is empty. From field: %s",move.getFrom()));
        }

        //check whether move affects future castling and set flags accordingly
        if(getPiece(move.getFrom()).getTypeOfFigure() == Type.KING){
            if(getPiece(move.getFrom()).getColor() == Color.WHITE){
                whiteShortCastling = false;
                whiteLongCastling = false;
            }else{
                blackShortCastling = false;
                blackLongCastling = false;
            }
        } else if (getPiece(move.getFrom()).getTypeOfFigure() == Type.ROOK) {
            if(getPiece(move.getFrom()).getColor() == Color.WHITE){
                if (move.getFrom().getRow() == Row.A){
                    whiteLongCastling = false;
                }else if (move.getFrom().getRow() == Row.H){
                    whiteShortCastling = false;
                }
            }else{
                if (move.getFrom().getRow() == Row.A){
                    blackLongCastling = false;
                }else if (move.getFrom().getRow() == Row.H){
                    blackShortCastling = false;
                }
            }
        }

        // check if the move to play is an en passant move
        if ((getPiece(move.getFrom()).equals(new Piece(Type.PAWN, Color.WHITE)) || getPiece(move.getFrom()).equals(new Piece(Type.PAWN, Color.BLACK))) &&
                (!move.getTo().getLine().equals(move.getFrom().getLine()) && !move.getTo().getRow().equals(move.getFrom().getRow())) &&
                getPiece(move.getTo()) == null) {

            // remove the indirectly captured pawn
            setPiece(null, new Field(move.getFrom().getLine(), move.getTo().getRow()));
        }

        //set piece on to-field
        setPiece(getPiece(move.getFrom()),move.getTo());
        //delete piece from from-field
        setPiece(null,move.getFrom());

        //check for castling
        if(getPiece(move.getTo()).getTypeOfFigure() == Type.KING){
            //check for white short castling
            if(move.getFrom().getLine() == Line.ONE
                    && move.getFrom().getRow() == Row.E
                    && move.getTo().getLine() == Line.ONE
                    && move.getTo().getRow() == Row.G){
                //move rook
                setPiece(new Piece(Type.ROOK, Color.WHITE), new Field(Line.ONE, Row.F));
                setPiece(null, new Field(Line.ONE, Row.H));

            } else if (move.getFrom().getLine() == Line.ONE//check for white long castling
                    && move.getFrom().getRow() == Row.E
                    && move.getTo().getLine() == Line.ONE
                    && move.getTo().getRow() == Row.C) {
                //move rook
                setPiece(new Piece(Type.ROOK, Color.WHITE), new Field(Line.ONE, Row.D));
                setPiece(null, new Field(Line.ONE, Row.A));

            } else if (move.getFrom().getLine() == Line.EIGHT
                    && move.getFrom().getRow() == Row.E
                    && move.getTo().getLine() == Line.EIGHT
                    && move.getTo().getRow() == Row.G) {//check for black short castling
                //move rook
                setPiece(new Piece(Type.ROOK, Color.BLACK), new Field(Line.EIGHT, Row.F));
                setPiece(null, new Field(Line.EIGHT, Row.H));

            } else if (move.getFrom().getLine() == Line.EIGHT
                    && move.getFrom().getRow() == Row.E
                    && move.getTo().getLine() == Line.EIGHT
                    && move.getTo().getRow() == Row.C) {//check for black long castling
                //update pieces
                setPiece(new Piece(Type.ROOK, Color.BLACK), new Field(Line.EIGHT, Row.D));
                setPiece(null, new Field(Line.EIGHT, Row.A));
            }
        }

        //check whether move is a promotion
        if(move.getPromoteTo() != null){
            setPiece(new Piece(move.getPromoteTo(), getPiece(move.getTo()).getColor()), move.getTo());
        }

        //check for en passant
        if(getPiece(move.getTo()).getTypeOfFigure() == Type.PAWN){
            if(move.getFrom().getLine() == Line.TWO){
                if(move.getTo().getLine() == Line.FOUR){
                    this.enPassantField = new Field(Line.THREE, move.getFrom().getRow());
                }else{
                    this.enPassantField = null;
                }
            }else if(move.getFrom().getLine() == Line.SEVEN){
                if(move.getTo().getLine() == Line.FIVE){
                    this.enPassantField = new Field(Line.SIX, move.getFrom().getRow());
                }else{
                    this.enPassantField = null;
                }
            }else{
                this.enPassantField = null;
            }
        }else {
            this.enPassantField = null;
        }

        if(this.nextColor==Color.BLACK){
            this.nextColor = Color.WHITE;
        }else {
            this.nextColor = Color.BLACK;
        }

        moveCounter++;
    }

    public int pieceCount() {
        return (Arrays.stream(this.pieces).map(
                l -> Arrays.stream(l)
                        .filter(Objects::nonNull)
                        .count()
        ).reduce(Long::sum)
        ).map(Long::intValue).orElse(0);
    }

    /**
     * Returns the piece from the given field
     * @param field the field to get the piece from
     * @return the piece chosen by the field
     */
    public Piece getPiece(Field field) {
        return pieces[field.getLine().ordinal()][field.getRow().ordinal()];
    }

    /**
     * Sets the specified piece on the specified field of the internal board (pieces)
     * @param piece the piece to be set
     * @param field the field to set the piece on
     */
    public void setPiece(Piece piece, Field field) {
        pieces[field.getLine().ordinal()][field.getRow().ordinal()] = piece;
    }

    /**
     * @return the color of the next player in turn.
     */
    public Color getNextColor() {
        return nextColor;
    }

    /**
     * Sets the color of the next player in turn.
     * @param nextColor the color of the next player.
     */
    public void setNextColor(Color nextColor) {
        this.nextColor = nextColor;
    }


    public boolean getWhiteShortCastling(){
        return this.whiteShortCastling;
    }


    public boolean getWhiteLongCastling(){
        return this.whiteLongCastling;
    }


    public boolean getBlackShortCastling(){
        return this.blackShortCastling;
    }


    public boolean getBlackLongCastling(){
        return this.blackLongCastling;
    }


    public void setWhiteShortCastling(boolean whiteShortCastling){
        this.whiteShortCastling = whiteShortCastling;
    }


    public void setWhiteLongCastling(boolean whiteLongCastling){
        this.whiteLongCastling = whiteLongCastling;
    }


    public void setBlackShortCastling(boolean blackShortCastling){
        this.blackShortCastling = blackShortCastling;
    }


    public void setBlackLongCastling(boolean blackLongCastling){
        this.blackLongCastling = blackLongCastling;
    }

    public int getMoveCounter(){
        return  this.moveCounter;
    }

    public void setMoveCounter(int moveCounter){
        this.moveCounter = moveCounter;
    }

    @Override
    public String toString() {
        String board = "";
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                board += pieces[i][j] == null? "____ " : pieces[i][j].getTypeOfFigure() + " ";
            }
            board += "\n";
        }

        return board;
    }

    public Board deepClone(){
        Board b = new Board();

        final Piece[][] result = new Piece[8][8];
        for (int i = 0; i < this.pieces.length; i++) {
            for(int j = 0; j < this.pieces[i].length; j++){
                result[i][j] = this.pieces[i][j] == null? null : new Piece(this.pieces[i][j].getTypeOfFigure(), this.pieces[i][j].getColor());
            }

        }
        b.nextColor = this.getNextColor();
        b.pieces = result;
        b.moveCounter = this.moveCounter;
        b.blackLongCastling = this.blackLongCastling;
        b.whiteLongCastling = this.whiteLongCastling;
        b.whiteShortCastling = this.whiteShortCastling;
        b.blackShortCastling = this.blackShortCastling;
        return b;
    }

    public Field getEnPassantField() {
        return enPassantField;
    }

    public void setEnPassantField(Field enPassantField) {
        this.enPassantField = enPassantField;
    }
}
