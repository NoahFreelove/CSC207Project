package com.project.game.Scripts;

import com.project.engine.Scripting.IScriptable;
import org.json.JSONObject;

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

    @Override
    public JSONObject serialize() {
        JSONObject out = new JSONObject();
        out.put("friction", friction);
        return out;
    }

    @Override
    public void deserialize(JSONObject data) {
        friction = data.getDouble("friction");
    }
}
