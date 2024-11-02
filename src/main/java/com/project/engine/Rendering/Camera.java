package com.project.engine.Rendering;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Tuple;
import com.project.engine.Scripting.IScriptable;
import org.json.JSONObject;

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

        return new Tuple<>(attached.getTransform().getPositionX()+ offsetX,
                attached.getTransform().getPositionX() + offsetY);
    }

    public double getCameraX() {
        if (attached == null) {
            return offsetX;
        }
        return attached.getTransform().getPositionX() + offsetX;
    }

    public double getCameraY() {
        if (attached == null) {
            return offsetY;
        }
        return attached.getTransform().getPositionY() + offsetY;
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

    @Override
    public Class<?> attachedClass() {
        return getClass();
    }

    @Override
    public void deserialize(JSONObject data) {
        offsetX = data.getDouble("offsetX");
        offsetY = data.getDouble("offsetY");
    }

    @Override
    public JSONObject serialize() {
        JSONObject output = new JSONObject();
        output.put("offsetX", offsetX);
        output.put("offsetY", offsetY);
        return output;
    }
}
