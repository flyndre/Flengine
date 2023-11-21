package de.flyndre.flengine.datamodel;

import de.flyndre.flengine.datamodel.enums.EngineDifficulty;

/**
 * This class provides a data model to store options selected over UCI commands.
 * It's currently not implemented and only there to be build in the functions to add later these options.
 * @author Lukas
 */
public class Options {

    /**
     * Represents the difficulty of the engine
     * Values are specified in the enum EngineDifficulty ranging from 1 (easiest) to 100 (hardest)
     */
    private EngineDifficulty engineDifficulty = EngineDifficulty.Normal;

    public EngineDifficulty getEngineDifficulty() {
        return engineDifficulty;
    }

    public void setEngineDifficulty(EngineDifficulty engineDifficulty) {
        this.engineDifficulty = engineDifficulty;
    }
}
