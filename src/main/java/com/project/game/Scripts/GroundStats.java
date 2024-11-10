package com.project.game.Scripts;

import com.project.engine.Scripting.IScriptable;

public class GroundStats implements IScriptable {
    private double friction = 0;

    public GroundStats(double friction) {
        this.friction = friction;
    }

    public GroundStats() {
        this.friction = 0;
    }


    public double getFriction() {
        return friction;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }
}
