package de.flyndre.flengine.datamodel;

import de.flyndre.flengine.datamodel.enums.optionenums.EngineDifficulty;

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
    private EngineDifficulty engineDifficulty = EngineDifficulty.Normal;

    /**
     * Specifies whether UCI Debug Mode is activated.
     * DOES NOT NEED TO BE SPECIFIED BY GUI WITH OPTION COMMAND!
     * Gets set with the debug command
     */
    private boolean debugMode = false;

    public EngineDifficulty getEngineDifficulty() {
        return engineDifficulty;
    }

    public void setEngineDifficulty(EngineDifficulty engineDifficulty) {
        this.engineDifficulty = engineDifficulty;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
}
