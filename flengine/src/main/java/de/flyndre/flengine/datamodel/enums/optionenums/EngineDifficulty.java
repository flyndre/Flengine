package de.flyndre.flengine.datamodel.enums.optionenums;

/**
 * Used to tweak the engine's difficulty.
 * A lower value makes it less difficult, a higher one more difficult.
 * The value 1 gives all moves the same probability.
 * @author Paul
 */
public enum EngineDifficulty {
    Easy(1),
    Normal(8),
    Hard(100);

    private final int difficultyValue;
    EngineDifficulty(int difficultyValue){
        this.difficultyValue = difficultyValue;
    }

    public int getDifficultyValue(){
        return this.difficultyValue;
    }
}
