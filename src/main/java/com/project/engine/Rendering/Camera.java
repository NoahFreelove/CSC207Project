package com.project.engine.Rendering;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Tuple;
import com.project.engine.Scripting.IScriptable;

public class Camera implements IScriptable {
    private GameObject attached;
    private double offsetX;
    private double offsetY;

    @Override
    public void update(GameObject parent, double deltaTime) {
        attached = parent;
    }

    public Tuple<Double, Double> getCameraPosition() {
        if (attached == null) {
            return new Tuple<>(offsetX, offsetY);
        }

        return new Tuple<>(attached.getXPosition() + offsetX, attached.getYPosition() + offsetY);
    }

    public double getCameraX() {
        if (attached == null) {
            return offsetX;
        }
        return attached.getXPosition() + offsetX;
    }

    public double getCameraY() {
        if (attached == null) {
            return offsetY;
        }
        return attached.getYPosition() + offsetY;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public void translate(double x, double y){
        offsetX += x;
        offsetY += y;
    }
}
