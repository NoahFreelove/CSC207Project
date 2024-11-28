package com.project.use_cases.player_death_count;

import com.project.entity.scripting.IScriptable;

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
