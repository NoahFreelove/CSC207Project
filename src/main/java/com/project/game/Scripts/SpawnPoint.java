package com.project.game.Scripts;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Tuple;
import com.project.engine.Scripting.IScriptable;
import com.project.physics.PhysicsBody.RigidBody2D;


public class SpawnPoint implements IScriptable {
    private Tuple<Double, Double> spawnPoint;

    private GameObject parentGO = null;

    private double respawnDelay = 1.42; // delay in seconds

    private double timeSinceDeath = 0.0;

    private boolean isDead = false;

    public SpawnPoint(double x, double y) {
        spawnPoint = new Tuple<>(x, y);
    }

    public SpawnPoint() {
        spawnPoint = new Tuple<>(0.0, 0.0);
    }

    public void setSpawnPoint(double x, double y) {
        spawnPoint.setFirst(x);
        spawnPoint.setSecond(y);
    }

    public Tuple<Double, Double> getSpawnPoint() {
        return spawnPoint;
    }

    @Override
    public void start(GameObject parent) {
        parentGO = parent;
        parent.getTransform().setPosition(spawnPoint);

        DeathJoke dj = parent.getScriptable(DeathJoke.class);

        if (dj != null) {
            Thread t = new Thread(dj::generateJoke);
            t.start();
        }
    }

    @Override
    public void update(GameObject parent, double deltaTime) {
        parentGO = parent;
        if (parent.getTransform().getPositionY() >= 800) {
            Tuple<Double, Double> death_pt = new Tuple<>(parent.getTransform().getPositionX(), parent.getTransform().getPositionY() - 300);
            parent.getTransform().setPosition(death_pt);
            parent.getScriptable(RigidBody2D.class).resetY();
            parent.getScriptable(RigidBody2D.class).resetX();
            parent.getScriptable(RigidBody2D.class).addForce(0, 1.15f*-300600);
            parent.getTransform().setScaleX(2.0);
            isDead = true;
            timeSinceDeath = 0.0;
        }
        if (isDead){
            timeSinceDeath += deltaTime;
            if (timeSinceDeath >= respawnDelay) {
                respawn();
                isDead = false;
                parent.getTransform().setScaleX(1.0);
            }
        }
    }

    public void respawn() {
        if (parentGO == null) {
            return;
        }

        GameObject parent = parentGO;
        parent.getTransform().setPosition(spawnPoint);

        RigidBody2D rb = parent.getScriptable(RigidBody2D.class);
        if (rb != null) {
            rb.resetX();
            rb.resetY();
        }

        DeathJoke dj = parent.getScriptable(DeathJoke.class);
        if (dj != null) {
            dj.readJoke();

            Thread t = new Thread(dj::generateJoke);
            t.start();
        }
    }
}
