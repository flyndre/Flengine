package de.flyndre.flengine.datamodel;

import de.flyndre.flengine.datamodel.enums.Type;

/**
 * This class represents a single move of a figure from one field to another.
 * @author Lukas
 */
public class Move {
    private Field from;
    private Field to;
    /**
     * Contains the type to switch to if a change should be performed. If this move is no promotion the value is null.
     */
    private Type promoteTo;

    /**
     * Creates a new instance.
     * @param from the field from which the move comes
     * @param to the field to which the move goes
     * @param promoteTo the type to which to change after the move is completed
     */
    public Move(Field from, Field to, Type promoteTo){
        this.from=from;
        this.to=to;
        this.promoteTo = promoteTo;
    }
    public Move(Field from, Field to){
        this(from,to,null);
    }

    public Field getFrom() {
        return from;
    }

    public Field getTo() {
        return to;
    }

    public Type getPromoteTo() {
        return promoteTo;
    }

    public void setFrom(Field from) {
        this.from = from;
    }

    public void setTo(Field to) {
        this.to = to;
    }

    public void setPromoteTo(Type promoteTo) {
        this.promoteTo = promoteTo;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        Move move = (Move) obj;
        if (move.getFrom() == null || move.getTo() == null) return false;
        return from.equals(move.getFrom()) && to.equals(move.getTo()) &&
            (promoteTo == null ? promoteTo == move.getPromoteTo() : promoteTo.equals(move.getPromoteTo()));
    }

    @Override
    public int hashCode() { return from.hashCode() * 64 + to.hashCode(); }

    @Override
    public String toString() { return from.toString() + "-" + to.toString(); }
}
