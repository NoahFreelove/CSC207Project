package com.project.use_cases.core.prebuilts.scripts;

import com.project.entity.core.GameObject;
import com.project.entity.core.Tuple;
import com.project.entity.scripting.IScriptable;
import com.project.entity.physics.physics_body.RigidBody2D;
import com.project.use_cases.player_move.PlayerMoveInteractor;
import org.json.JSONObject;


public class SpawnPoint implements IScriptable {
    private final Tuple<Double, Double> spawnPoint;

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

            RigidBody2D rb = parent.getScriptable(RigidBody2D.class, true);

            if (rb != null) {
                rb.resetX();
                rb.resetY();
                parent.enableScript(rb);
            }

            PlayerMoveInteractor mc = parent.getScriptable(PlayerMoveInteractor.class);
            if (mc != null) {
                mc.setCanMove(true);
                mc.setCanJump(true);
                mc.reset(parent);

            }

            respawnQueued = false;
        }
    }

    @Override
    public void reset(GameObject parent) {
        respawnQueued = true;
        update(parent, 0.0);
    }

    @Override
    public JSONObject serialize() {
        JSONObject output = new JSONObject();
        output.put("x", spawnPoint.getFirst());
        output.put("y", spawnPoint.getSecond());
        return output;
    }

    @Override
    public void deserialize(JSONObject data) {
        spawnPoint.setFirst(data.getDouble("x"));
        spawnPoint.setSecond(data.getDouble("y"));
    }
}
