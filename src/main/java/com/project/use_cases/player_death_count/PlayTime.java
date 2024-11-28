package com.project.use_cases.player_death_count;

import com.project.entity.scripting.IScriptable;

public class PlayTime implements IScriptable {
    private int playTime;

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public int getPlayTime() {
        return playTime;
    }

    public String completeGame() {
        return "Your total play time was: " + playTime;
    }
}
