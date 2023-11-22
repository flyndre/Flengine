package de.flyndre.flengine.datamodel;


import de.flyndre.flengine.datamodel.enums.Color;
import de.flyndre.flengine.datamodel.enums.Type;

/**
 * Represents a chess piece.
 * @author Ruben
 */
public class Piece {
    /**
     * The color of the piece
     */
    private Color color;
    /**
     * The type of the piece and its role.
     */
    private Type typeOfFigure;

    /**
     * Creates a new instance of the piece.
     * @param type the type of the chess piece.
     * @param color the color of the team.
     */
    public Piece(Type type, Color color){
        this.typeOfFigure=type;
        this.color=color;
    }

    public Color getColor() {
        return color;
    }

    public Type getTypeOfFigure() {
        return typeOfFigure;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setTypeOfFigure(Type typeOfFigure) {
        this.typeOfFigure = typeOfFigure;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        Piece piece = (Piece) obj;
        if (piece.getColor() == null || piece.getTypeOfFigure() == null) return false;
        return color.equals(piece.getColor()) && typeOfFigure.equals(piece.getTypeOfFigure());
    }

    @Override
    public int hashCode() { return typeOfFigure.ordinal() * 2 + (color.equals(Color.WHITE) ? 0 : 1); }

    @Override
    public String toString() { return color.name() + " " + typeOfFigure.toString();}
}
