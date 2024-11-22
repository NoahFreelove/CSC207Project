package com.project.game.Scripts;

import com.project.engine.Core.GameObject;
import com.project.engine.Scripting.IScriptable;
import com.project.engine.Physics.PhysicsBody.RigidBody2D;
import org.json.JSONObject;

public class PlayerDeath implements IScriptable {
    private boolean dead = false;
    private double respawnDelay = 1.42; // delay in seconds
    private double timeScinceDeath = 0;
    private final double COOLDOWN_TIME = 0.1; //Death cooldown in seconds
    private double deathCooldown = 0;

    public PlayerDeath() {}

    @Override
    public void start(GameObject parent) {
        DeathJoke dj = parent.getScriptable(DeathJoke.class);

        if (dj != null) {
            dj.generateJoke();
        }
    }

    public void update(GameObject parent, double delta) {
        deathCooldown += delta; // Add death cooldown to prevent double firring of death logic

        if (!dead) { return; }

        if (timeScinceDeath == 0) {
            die(parent,300600);
        }

        timeScinceDeath += delta;

        if (timeScinceDeath >= respawnDelay) {
            timeScinceDeath = 0;
            deathCooldown = 0;

            dead = false;

            parent.enableScript(parent.getScriptable(SimpleCollider.class, true)); // now we want hard collision events with anything

            parent.getLinkedScene().getCamera().setFollowX(true);
            parent.getTransform().setScaleX(1.0);
            parent.getScriptable(SpawnPoint.class).queueRespawn();
            parent.addTag("player");

            parent.getLinkedScene().reset();
        }

    }

    public void queueDeath() {
        if (!dead && deathCooldown >= COOLDOWN_TIME) {
            this.dead = true;
        }
    }

    private void die(GameObject parent, float deathForce) {
        parent.getScriptable(RigidBody2D.class).resetY();
        parent.getScriptable(RigidBody2D.class).resetX();
        parent.getScriptable(RigidBody2D.class).addForce(0, -1f*deathForce);

        parent.getLinkedScene().getCamera().setFollowX(false);
        parent.getTransform().setScaleX(2.0 * Math.signum(parent.getTransform().getScaleX()));
        parent.getTransform().setPositionX(parent.getTransform().getPositionX() - 0.25 *    Math.abs(parent.getTransform().getWidth()));

        DeathJoke dj = parent.getScriptable(DeathJoke.class);
        if (dj != null) {
            dj.readJoke();
            dj.generateJoke();
        }

        MovementController mc = parent.getScriptable(MovementController.class);
        if (mc != null) {
            mc.setCanMove(false);
            mc.setCanJump(false);
            mc.enableAnimation = false;
        }

        parent.disableScript(parent.getScriptable(SimpleCollider.class, true)); // now we want hard collision events with anything


        parent.removeTag("player"); // dont trigger other events when dead
    }

    @Override
    public JSONObject serialize() {
        return new JSONObject();
    }
}
