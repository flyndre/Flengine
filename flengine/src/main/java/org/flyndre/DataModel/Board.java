package org.flyndre.DataModel;

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
}
