package com.project.use_cases.player_death_count;

import com.project.entity.core.GameObject;
import com.project.entity.scripting.IScriptable;
import com.project.entity.physics.physics_body.RigidBody2D;
import com.project.use_cases.player_move.PlayerMoveInteractor;
import com.project.use_cases.play_prebuilt_levels.scripts.SimpleCollider;
import com.project.use_cases.play_prebuilt_levels.scripts.SpawnPoint;
import org.json.JSONObject;

import static com.project.use_cases.prebuilts.game_ui.UIFactory.deathCounter;

public class PlayerDeathInteractor implements IScriptable {
    private boolean dead = false;
    private double respawnDelay = 1.42; // delay in seconds
    private double timeScinceDeath = 0;
    private final double COOLDOWN_TIME = 0.1; //Death cooldown in seconds
    private double deathCooldown = 0;
    private static int deathCount = 0;

    public PlayerDeathInteractor() {}

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

    public static void updateDeathCount() {
        deathCounter.setText("Deaths: " + (PlayerDeathInteractor.getDeathCount()));
    }

    public static void resetDeathCount() {
        deathCount = 0;
    }

    public void execute() {
        if (!dead && deathCooldown >= COOLDOWN_TIME) {
            this.dead = true;
            this.deathCount++;
            updateDeathCount();
        }
    }

    public static int getDeathCount() {
        return deathCount;
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

        PlayerMoveInteractor mc = parent.getScriptable(PlayerMoveInteractor.class);
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
