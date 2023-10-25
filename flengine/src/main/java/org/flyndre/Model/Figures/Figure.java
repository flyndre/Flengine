package de.flyndre.flengine.Model.Figures;

import de.flyndre.flengine.Model.Board.Field;

public abstract class Figure {
    Color color;
    private Field currentPosition;
    public abstract void move();
}
