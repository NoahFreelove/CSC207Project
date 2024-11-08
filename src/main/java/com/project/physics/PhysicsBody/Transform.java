package com.project.physics.PhysicsBody;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Tuple;
import com.project.engine.Scripting.IScriptable;
import com.project.engine.Serialization.ISerializable;
import org.json.JSONArray;
import org.json.JSONObject;

public class Transform implements IScriptable, ISerializable {
    private Tuple<Double, Double> position, scale;
    private Tuple<Integer, Integer> dimensions = new Tuple<>(1, 1);
    private float z_index = 0;

    // Swing deals with rectangles so watch out for cropping
    private float rotation = 0;

    // Check the update method for the rationale on the existence of these variables
    private Tuple<Double, Double> staged_position, staged_scale;
    private Tuple<Integer, Integer> staged_dimensions = new Tuple<>(1, 1);;
    private float staged_rotation;

    public Transform(GameObject parent, Tuple<Double, Double> position){
        start(position);
    }

    public void start(Tuple<Double, Double> position){
        this.position = position;
        this.rotation = 0;
        this.scale = new Tuple<>(1.0, 1.0);

        this.staged_position = new Tuple<>(0.0, 0.0);
        this.staged_scale =  new Tuple<>(1.0, 1.0);
        this.staged_rotation = 0;
    }


    /**
     * NOTE: This is relative to default orientation.
     */
    public void faceLeft() {
        if (staged_scale.getFirst() > 0) {
            staged_scale.setFirst(staged_scale.getFirst() * -1);
        }
    }

    /**
     * NOTE: This is relative to default orientation.
     */
    public void faceRight() {
        if (staged_scale.getFirst() < 0) {
            staged_scale.setFirst(staged_scale.getFirst() * -1);
        }
    }

    /**
     * NOTE: This is relative to default orientation.
     */
    public void faceUp() {
        if (staged_scale.getSecond() < 0) {
            staged_scale.setSecond(staged_scale.getSecond() * -1);
        }
    }

    /**
     * NOTE: This is relative to default orientation.
     */
    public void faceDown() {
        if (staged_scale.getFirst() > 0) {
            staged_scale.setFirst(staged_scale.getFirst() * -1);
        }
    }

    public void translate(Double x, Double y){
        setPositionX(this.staged_position.getFirst() + x);
        setPositionY(this.staged_position.getSecond() + y);
    }

    public void setPositionX(double x){
        staged_position.setFirst(x);
    }

    public void setPositionY(double y){
        staged_position.setSecond(y);
    }

    public void setPosition(Tuple<Double, Double> position){
        this.staged_position = new Tuple<>(position.getFirst(), position.getSecond());
    }

    public void setPosition(double posX, double posY){
        this.staged_position.setFirst(posX);
        this.staged_position.setSecond(posY);
    }

    public void setScaleX(double x){
        staged_scale.setFirst(x);
    }

    public void setScaleY(double y){
        staged_scale.setSecond(y);
    }

    public void setScale(Tuple<Double, Double> scale){
        this.staged_scale = scale;
    }

    public void setWidth(int w) {
        staged_dimensions.setFirst(w);
    }

    public void setHeight(int h) {
        staged_dimensions.setSecond(h);
    }

    public void setDimensions(Tuple<Integer, Integer> dimension){
        this.staged_dimensions = dimension;
    }

    /**
     * Sets rotation in degrees.
     */
    public void setRotation(float angle){
        this.staged_rotation = angle;
    }


    public Double getPositionX() {
        return position.getFirst();
    }

    public Double getPositionY() {
        return position.getSecond();
    }

    public Tuple<Double, Double> getPosition() {
        return new Tuple<>(position.getFirst(), position.getSecond());
    }

    public Double getScaleX() {
        return scale.getFirst();
    }

    public Double getScaleY() {
        return scale.getSecond();
    }

    public Tuple<Double, Double> getScale(boolean mutable) {
        return mutable ? staged_scale : new Tuple<>(scale.getFirst(), scale.getSecond());
    }

    public int getWidth() {
        return (int)(dimensions.getFirst() * scale.getFirst());
    }

    public int getHeight() {
        return (int)(dimensions.getSecond() * scale.getSecond());
    }

    public float getRotation() {
        return rotation;
    }

    public float getZIndex() {
        return z_index;
    }

    public void setZIndex(float z_index) {
        this.z_index = z_index;
    }

    /**
     * This method exists to restrict all changes in Transform until a set deltaTime
     * has passed.
     * The reason we need to do this is so that transforms update at regular time intervals.
     * This means that all logic (ex for collisions) takes place at regular intervals.
     * If this wasn't the case, player might collide with an object before the renderer shows
     * the player that they have hit something, leading to a not so fun experience.
     *
     * This design makes getters delayed by a frame, so game logic will take place based on
     * where the user sees the object.
     */
    @Override
    public void update(GameObject parent, double dt) {
        this.position = this.staged_position;

        scale = staged_scale;
        dimensions = staged_dimensions;
        rotation = staged_rotation;
    }

    @Override
    public JSONObject serialize() {
        JSONObject output = new JSONObject();
        JSONArray posArr = new JSONArray();
        posArr.put(position.getFirst());
        posArr.put(position.getSecond());
        output.put("position", posArr);

        JSONArray scaleArr = new JSONArray();
        scaleArr.put(scale.getFirst());
        scaleArr.put(scale.getSecond());
        output.put("scale", scaleArr);

        output.put("rotation", rotation);
        output.put("zIndex", z_index);
        return output;
    }

    @Override
    public void deserialize(JSONObject data) {
        JSONArray posArr = data.getJSONArray("position");
        position = new Tuple<>(posArr.getDouble(0), posArr.getDouble(1));

        JSONArray scaleArr = data.getJSONArray("scale");
        scale = new Tuple<>(scaleArr.getDouble(0), scaleArr.getDouble(1));

        rotation = data.getFloat("rotation");
        z_index = data.getFloat("zIndex");

        this.staged_rotation = rotation;
        this.staged_position.setFirst(position.getFirst());
        this.staged_position.setSecond(position.getSecond());
        this.staged_scale.setFirst(scale.getFirst());
        this.staged_scale.setSecond(scale.getSecond());
    }
}
