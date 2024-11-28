package com.project.use_cases.play_prebuilt_levels.scripts;

import com.project.entity.core.GameObject;
import com.project.entity.scripting.IScriptable;
import org.json.JSONObject;

public class InterpolationMove implements IScriptable {
    private double x, y;
    private double targetX, targetY;
    private double speed;
    private boolean active;

    public InterpolationMove() {
        this(0, 0, 0);
    }

    public InterpolationMove(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.active = false;
    }

    public void setTarget(double x, double y) {
        this.targetX = x;
        this.targetY = y;
        this.active = false;
    }

    public void update(GameObject parent, double deltaTime) {
        if (!active) {
            return;
        }

        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < speed * deltaTime) {
            x = targetX;
            y = targetY;

            active = false;
        } else {
            x += dx / distance * speed * deltaTime;
            y += dy / distance * speed * deltaTime;
            parent.getTransform().setPosition(x, y);
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public JSONObject serialize() {
        JSONObject out = new JSONObject();
        out.put("x", x);
        out.put("y", y);
        out.put("speed", speed);
        out.put("active", active);
        out.put("targetX", targetX);
        out.put("targetY", targetY);
        return out;
    }

    @Override
    public void deserialize(JSONObject data) {
        x = data.getDouble("x");
        y = data.getDouble("y");
        speed = data.getDouble("speed");
        active = data.getBoolean("active");
        targetX = data.getDouble("targetX");
        targetY = data.getDouble("targetY");
    }
}
