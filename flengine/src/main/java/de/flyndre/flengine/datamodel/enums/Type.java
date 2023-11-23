package de.flyndre.flengine.datamodel.enums;

public enum Type {
    PAWN(1),
    ROOK(5),
    BISHOP(3),
    KNIGHT(3),
    QUEEN(9),
    KING(100);

    private final int value;

    public int getValue(){
        return this.value;
    }

    Type(int value) {
        this.value = value;
    }
}
