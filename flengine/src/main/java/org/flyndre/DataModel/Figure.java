package org.flyndre.DataModel;


import org.flyndre.DataModel.EnumTypes.Color;
import org.flyndre.DataModel.EnumTypes.Type;

/**
 * Represents a chess figure.
 * @author Ruben
 */
public class Figure {
    /**
     * The color of the figure
     */
    private Color color;
    /**
     * The type of the figure and its role.
     */
    private Type typeOfFigure;

    /**
     * Creates a new instance of the Figure.
     * @param type the type of the chess figure.
     * @param color the color of the team.
     */
    public Figure(Type type, Color color){
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
}
