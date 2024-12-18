package com.project.entity.rendering;

import com.project.entity.scripting.IScriptable;
import com.project.entity.core.GameObject;
import com.project.entity.core.Tuple;
import org.json.JSONObject;

public class Camera implements IScriptable {
    private GameObject attached;

    private boolean followX = true;
    private boolean followY = true;

    private double offsetX;
    private double offsetY;

    private double positionX;
    private double positionY;

    @Override
    public void update(GameObject parent, double deltaTime) {
        attached = parent;
        positionX = attached.getTransform().getPositionX();
        positionY = attached.getTransform().getPositionY();
    }

    public Tuple<Double, Double> getCameraPosition() {
        if (attached == null) {
            return new Tuple<>(offsetX, offsetY);
        }

        return new Tuple<>(attached.getTransform().getPositionX()+ offsetX,
                attached.getTransform().getPositionX() + offsetY);
    }

    public double getCameraX() {
        if (attached != null && followX) {
            positionX = attached.getTransform().getPositionX();
        }

        return positionX + offsetX;
    }

    public double getCameraY() {
        if (attached != null && followY) {
            positionY = attached.getTransform().getPositionY();
        }

        return positionY + offsetY;
    }

    public void setFollowX(boolean followX) {
        this.followX = followX;
    }

    public void setFollowY(boolean followY) {
        this.followY = followY;
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
    public void reset(GameObject parent) {
        attached = null;
        followX = false;
        followY = false;
    }

    public GameObject getAttached() {
        return attached;
    }

    public void reset() {
        attached = null;
        followX = false;
        followY = false;
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
