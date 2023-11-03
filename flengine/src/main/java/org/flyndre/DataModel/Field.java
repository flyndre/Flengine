package org.flyndre.DataModel;

/**
 * This class represents a field on the chess board.
 */
public class Field {
    private char line;
    private int row;

    /**
     * Creates a new instance of a field with the given parameters.
     * @param line the char of the line of the field.
     * @param row the index of the row of the field.
     * @throws IllegalArgumentException if one of the arguments are out of bounce.
     */
    public Field(char line, int row){
        setLine(line);
        setRow(row);
    }

    /**
     * Creates a new instance of a field.
     * @param field the string notation of the field coordinates. Ignoring all chars except the first two.
     */
    public Field(String field){
        this(field.charAt(0),field.charAt(1));
    }

    public char getLine() {
        return line;
    }

    public int getRow() {
        return row;
    }

    public void setLine(char line) {
        if('a'<=line&&line<='h') {
            this.line = line;
        }else {
            throw new IllegalArgumentException(String.format("Expected a char between a and h. Given:%c",line));
        }
    }

    public void setRow(int row) {
        if(1<=row&&row<=8){
            this.row = row;
        }else {
            throw new IllegalArgumentException(String.format("Expected a int between 1 and 8. Get: %s",row));
        }

    }

    /**
     * @return the field in its string notation.
     */
    @Override
    public String toString() {
        return line+Integer.toString(row);
    }
}
