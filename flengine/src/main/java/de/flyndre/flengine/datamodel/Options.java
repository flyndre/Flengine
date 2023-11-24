package de.flyndre.flengine.datamodel;

import de.flyndre.flengine.datamodel.enums.Difficulty;

/**
 * This class provides a data model to store options selected over UCI commands.
 * It's currently not implemented and only there to be built in the functions to add later these options.
 * @author Lukas
 */
public class Options {

    /**
     * Used to tweak the difficulty of the engine.
     * Values are specified in the enum {@code EngineDifficulty}.
     */
    private Difficulty difficulty = Difficulty.NORMAL;

    /**
     * Specifies whether UCI Debug Mode is activated.
     * DOES NOT NEED TO BE SPECIFIED BY GUI WITH OPTION COMMAND!
     * Gets set with the debug command
     */
    private boolean debugMode = false;

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
}
