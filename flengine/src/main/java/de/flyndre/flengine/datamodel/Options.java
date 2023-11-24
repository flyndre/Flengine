package de.flyndre.flengine.datamodel;

import de.flyndre.flengine.datamodel.enums.Difficulty;

/**
 * This class provides a data model to store options specified via UCI commands.
 * @author Lukas
 */
public class Options {

    /**
     * Used to tweak the difficulty of the engine.
     * Values are specified in the enum {@code EngineDifficulty}.
     */
    private Difficulty difficulty = Difficulty.NORMAL;

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

}
