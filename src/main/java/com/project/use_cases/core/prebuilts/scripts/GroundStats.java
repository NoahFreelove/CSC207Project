package com.project.use_cases.core.prebuilts.scripts;

import com.project.entity.scripting.IScriptable;
import org.json.JSONObject;

public class GroundStats implements IScriptable {
    private double friction;

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
