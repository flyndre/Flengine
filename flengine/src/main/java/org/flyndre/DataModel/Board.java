package org.flyndre.DataModel;

/**
 * @author Lukas
 * This class represents the chess board in the game logic.
 */
public class Board {
    /**
     * Represents the chess board with all figures on it.
     */
    private Piece[][] pieces = new Piece[8][8];

    public Piece[][] getFigures() {
        return pieces;
    }

    public void setFigures(Piece[][] pieces) {
        this.pieces = pieces;
    }
}
