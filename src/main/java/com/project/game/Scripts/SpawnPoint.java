package com.project.game.Scripts;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Tuple;
import com.project.engine.Scripting.IScriptable;
import com.project.physics.PhysicsBody.RigidBody2D;

public class SpawnPoint implements IScriptable {
    private Tuple<Double, Double> spawnPoint;

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
        parent.getTransform().setPosition(spawnPoint);

        DeathJoke dj = parent.getScriptable(DeathJoke.class);

        if (dj != null) {
            Thread t = new Thread(dj::generateJoke);
            t.start();
        }
    }

    @Override
    public void update(GameObject parent, double deltaTime) {
        if (parent.getTransform().getPositionY() >= 800) {
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
}
