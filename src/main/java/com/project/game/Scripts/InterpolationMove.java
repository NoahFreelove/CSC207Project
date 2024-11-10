package com.project.game.Scripts;

import com.project.engine.Core.GameObject;
import com.project.engine.Scripting.IScriptable;

public class InterpolationMove implements IScriptable {
    private double x, y;
    private double targetX, targetY;
    private double speed;
    private boolean active;

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
}
