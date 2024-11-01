package com.project.physics.PhysicsBody;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Tuple;
import com.project.engine.Scripting.IScriptable;

public class Transform implements IScriptable {
    private Tuple<Double, Double> position;
    private Tuple<Double, Double> scale;
    private float z_index = 0;

    // Swing deals with rectangles so watch out for cropping
    private float rotation = 0;

    // Check the update method for the rationale on the existence of these variables
    private Tuple<Double, Double> staged_position;
    private Tuple<Double, Double> staged_scale;
    private float staged_rotation;

    public Transform(GameObject parent, Tuple<Double, Double> position){
        start(position);
    }

    public void start(Tuple<Double, Double> position){
        this.position = position;
        this.rotation = 0;
        this.scale = new Tuple<>(1.0, 1.0);

        this.staged_position = this.position;
        this.staged_scale = this.scale;
        this.staged_rotation = this.rotation;
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
        if (staged_scale.getSecond() > 0) {
            staged_scale.setSecond(staged_scale.getSecond() * -1);
        }
    }

    /**
     * NOTE: This is relative to default orientation.
     */
    public void faceDown() {
        if (staged_scale.getFirst() < 0) {
            staged_scale.setFirst(staged_scale.getFirst() * -1);
        }
    }

    public void translate(Double x, Double y){
        setPositionX(this.position.getFirst() + x);
        setPositionY(this.position.getSecond() + y);
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
        position = staged_position;
        scale = staged_scale;
        rotation = staged_rotation;
    }
}
