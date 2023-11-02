package org.flyndre.DataModel;

/**
 * @author Lukas
 * This class represents the chess board in the game logic.
 */
public class Board {
    /**
     * Represents the chess board with all figures on it.
     */
    private Figure[][] figures = new Figure[8][8];

    public Figure[][] getFigures() {
        return figures;
    }

    public void setFigures(Figure[][] figures) {
        this.figures = figures;
    }
}
