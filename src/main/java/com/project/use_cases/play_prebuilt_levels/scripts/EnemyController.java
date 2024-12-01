package com.project.use_cases.play_prebuilt_levels.scripts;

import com.project.entity.core.GameObject;
import com.project.entity.physics.collision.BoxCollider;
import com.project.entity.physics.collision.CollisionVolume;
import com.project.entity.physics.collision.types.ECollisionVolume;
import com.project.entity.physics.physics_body.RigidBody2D;
import com.project.entity.scripting.IScriptable;
import com.project.use_cases.player_death_count.PlayerDeathInteractor;

public class EnemyController extends BoxCollider implements IScriptable {
    private int moveDirection = 1;
    private float moveSpeed = 1000;
    private final float ENEMY_DIE_THRESHOLD = 65;
    private boolean canSwitch = false;
    private double switchCooldown = 0.0;
    private final double ENEMY_DIR_SWITCH_TIME = 1;

    @Override
    public void update(GameObject parent, double deltaTime) {
        RigidBody2D rb = parent.getScriptable(RigidBody2D.class);

        if (rb != null) {
            rb.addForce(moveSpeed * moveDirection, 0);
        }

        if (!canSwitch) {
            switchCooldown += deltaTime;
            if (switchCooldown>= ENEMY_DIR_SWITCH_TIME) {
                canSwitch = true;
            }
        }

    }

    @Override
    public void onCollisionEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
        if (other.hasTag("ground")) {
            super.onCollisionEnter(parent, other, interactor);
        }

        RigidBody2D rb = parent.getScriptable(RigidBody2D.class);
        if (other.hasTag("player") && interactor.volumeType() == ECollisionVolume.COLLIDER) {
            if(parent.getTransform().getPositionY() - other.getTransform().getPositionY() >= ENEMY_DIE_THRESHOLD){
                parent.disableScript(rb);
                moveDirection = 1;
                parent.getTransform().setPosition(-1000, -1000);
                return;
            }

            PlayerDeathInteractor pd = other.getScriptable(PlayerDeathInteractor.class);

            if (pd != null) {
                pd.execute();
            }
        }

        if (other.hasTag("ground") && Math.abs(rb.getVelocityX()) == 0 &&
            canSwitch) {
            moveDirection *= -1;

            if (moveDirection == -1) {
                parent.getTransform().faceLeft();
            }
            else{
                parent.getTransform().faceRight();
            }
            canSwitch = false;
            switchCooldown = 0.0;
        }
    }

    @Override
    public void onCollisionContinue(GameObject parent, GameObject other, CollisionVolume interactor) {
        if (other.hasTag("ground")) {
            super.onCollisionContinue(parent, other, interactor);
        }
    }
}
