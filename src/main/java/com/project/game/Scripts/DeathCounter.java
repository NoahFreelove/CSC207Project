package com.project.game.Scripts;

import com.project.engine.Scripting.IScriptable;

public class DeathCounter implements IScriptable {
    private int DeathCounter = 0;

    public int getDeathCounter() {
        return DeathCounter;
    }

    public void setDeathCounter(int deathCounter) {
        DeathCounter = deathCounter;
    }

    public void addDeath() {
        DeathCounter++;
    }
}
