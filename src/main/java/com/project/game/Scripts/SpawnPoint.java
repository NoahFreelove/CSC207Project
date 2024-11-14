package com.project.game.Scripts;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Tuple;
import com.project.engine.Scripting.IScriptable;
import com.project.physics.PhysicsBody.RigidBody2D;


public class SpawnPoint implements IScriptable {
    private Tuple<Double, Double> spawnPoint;

    private boolean respawnQueued = false;

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

    public void queueRespawn() {
        respawnQueued = true;
    }

    @Override
    public void start(GameObject parent) {
        parent.getTransform().setPosition(spawnPoint);
    }

    @Override
    public void update(GameObject parent, double deltaTime) {
        if (respawnQueued) {
            start(parent);
            parent.getTransform().setPosition(spawnPoint);
            parent.getTransform().update(parent, 0); // Apply spawn position immediately

            RigidBody2D rb = parent.getScriptable(RigidBody2D.class);
            if (rb != null) {
                rb.resetX();
                rb.resetY();
            }

            MovementController mc = parent.getScriptable(MovementController.class);
            if (mc != null) {
                mc.setCanMove(true);
                mc.setCanJump(true);
            }

            respawnQueued = false;
        }
    }

    @Override
    public void reset(GameObject parent) {
        update(parent, 0.0);
    }
}
