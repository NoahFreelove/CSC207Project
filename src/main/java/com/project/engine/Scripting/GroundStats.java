package com.project.engine.Scripting;

import java.io.Serializable;

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
