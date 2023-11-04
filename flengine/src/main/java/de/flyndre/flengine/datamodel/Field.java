package de.flyndre.flengine.datamodel;

import de.flyndre.flengine.datamodel.enums.Line;
import de.flyndre.flengine.datamodel.enums.Row;
import de.flyndre.flengine.rules.BoardUtil;

/**
 * This class represents a field on the chess board.
 */
public class Field {
    /**
     * represents the line on the chess board (One, Two, Three, ... Eight)
     */
    private Line line;
    /**
     * represents the row on the chess board (A, B, C, ... H)
     */
    private Row row;

    /**
     * Creates a new instance of a field with the given parameters.
     * @param line the index of the line of the field.
     * @param row the char of the row of the field.
     * @throws IllegalArgumentException if one of the arguments are out of bounce.
     */
    public Field(Line line, Row row){
        setLine(line);
        setRow(row);
    }


    public Line getLine() {
        return line;
    }

    public Row getRow() {
        return row;
    }

    public void setLine(Line line) {
            this.line = line;
    }

    public void setRow(Row row) {
            this.row = row;
    }
}
