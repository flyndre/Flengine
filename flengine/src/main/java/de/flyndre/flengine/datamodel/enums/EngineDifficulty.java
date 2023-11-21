package de.flyndre.flengine.datamodel.enums;

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
