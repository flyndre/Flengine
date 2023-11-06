package de.flyndre.flengine.datamodel;

import de.flyndre.flengine.datamodel.enums.Color;

/**
 * @author Lukas
 * This class represents the chess board in the game logic.
 */
public class Board {
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
     * Executes a Move on the Board. If the target field is occupied it'll replace the piece on the target field with
     * the piece on the source field. It also changes the color of the next player.
     * @param move the move to be executed.
     * @throws IllegalArgumentException if the from field og the move is empty.
     */
    public void playMove(Move move){
        if (getPiece(move.getFrom())==null){
            throw new IllegalArgumentException(String.format("The from field is empty. From field: %s",move.getFrom()));
        }
        setPiece(getPiece(move.getFrom()),move.getTo());
        setPiece(null,move.getFrom());
        if(this.nextColor==Color.BLACK){
            this.nextColor = Color.WHITE;
        }else {
            this.nextColor = Color.BLACK;
        }
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
}
