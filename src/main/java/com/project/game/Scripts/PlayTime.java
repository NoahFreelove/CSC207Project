package com.project.game.Scripts;

import com.project.engine.Scripting.IScriptable;

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
