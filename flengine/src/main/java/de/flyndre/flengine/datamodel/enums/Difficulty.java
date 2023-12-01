package de.flyndre.flengine.datamodel.enums;

/**
 * Used to tweak the engine's difficulty.
 * A lower value makes it less difficult, a higher one more difficult.
 * The value 1 gives all moves the same probability.
 * @author Paul
 */
public enum Difficulty {
    EASY(1),
    NORMAL(8),
    HARD(100);

    private final int difficultyValue;
    Difficulty(int difficultyValue){
        this.difficultyValue = difficultyValue;
    }

    public int getValue() {
        return this.difficultyValue;
    }

    /**
     * Returns the name of the enum value with only the first letter capitalized.
     * @return the enum value name in a more readable form.
     */
    public String toReadableString() {
        var name = this.toString();
        return name.charAt(0) + name.substring(1).toLowerCase();
    }
}
