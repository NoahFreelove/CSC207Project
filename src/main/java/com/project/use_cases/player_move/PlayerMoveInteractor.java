package com.project.use_cases.player_move;

import com.project.use_cases.general.GameInteractor;
import com.project.entity.core.GameObject;
import com.project.use_cases.general.GameOutputData;
import com.project.entity.physics.physics_body.RigidBody2D;
import com.project.entity.rendering.SpriteRenderer;
import com.project.entity.scripting.IScriptable;
import com.project.entity.animation.AnimationManager;
import com.project.use_cases.player_animate.JumpAnimation;
import com.project.use_cases.player_animate.WalkAnimation;
import org.json.JSONObject;

public class PlayerMoveInteractor implements IScriptable {

    private float baseMoveSpeed = 10;
    private float moveSpeed = baseMoveSpeed;
    public boolean speedupPlatform = false;
    public boolean onIce = false;
    private float baseMaxVelocityX = 300;
    private float jumpForce = 1.15f;

    private boolean canMove = true;

    private boolean canJump = true;

    private RigidBody2D rb = null;

    private AnimationManager animationManager;

    private boolean isMoving = false;
    private boolean isJumping = false;

    public boolean enableAnimation = true;

    public PlayerMoveInteractor() {

    }

    @Override
    public void start(GameObject parent) {
        rb = parent.getScriptable(RigidBody2D.class);
        animationManager = new AnimationManager((SpriteRenderer) parent.getRenderables().next(), 128, 128);
        animationManager.addAnimation("walk", new WalkAnimation());
        animationManager.addAnimation("jump", new JumpAnimation());
    }

    @Override
    public void update(GameObject parent, double deltaTime) {
        GameOutputData win = GameInteractor.getInstance().getPrimaryWindow();
        PlayerMoveInputData ic = GameInteractor.getInstance().getInputCache();
        if (win == null)
            return;
        if (rb != null) {
            rb.attribs.maxVelocityX = (speedupPlatform) ? baseMaxVelocityX * 2 : baseMaxVelocityX;
            moveSpeed = (onIce) ? baseMoveSpeed / 10 : baseMoveSpeed;

            if (onIce){
                rb.attribs.maxVelocityX = baseMaxVelocityX * 3;
            }



        }
        if (canMove) {
            double actualSpeed = moveSpeed * deltaTime * 300;


            if (ic.isKeyPressed("A") || ic.isKeyPressed("LEFT")) {
                parent.getTransform().faceLeft();
                parent.getTransform().setRotation(0);
                move(parent, -actualSpeed, 0);

            }

            if (ic.isKeyPressed("D") || ic.isKeyPressed("RIGHT")) {
                parent.getTransform().faceRight();
                parent.getTransform().setRotation(0);
                move(parent, actualSpeed, 0);
            }
        }

        if ((ic.isKeyPressed("SPACE") || ic.isKeyPressed("W") || ic.isKeyPressed("UP"))  && canJump) {
                isJumping = true;
                animationManager.startMoving("jump");
                jump(parent, deltaTime);
                isMoving = true;

        }

        movementAnimation(ic);
    }

    private void movementAnimation(PlayerMoveInputData ic) {
        if (animationManager == null)
            return;

        if(!enableAnimation) {
            animationManager.stopMoving();
            return;
        }
        if (ic.isKeyPressed("A") || ic.isKeyPressed("LEFT") || ic.isKeyPressed("D") || ic.isKeyPressed("RIGHT")) {
            if (!isMoving && !isJumping) {
                animationManager.startMoving("walk");  // Start walking animation if not already moving
                isMoving = true;
            }
            if(isJumping)
                animationManager.startMoving("jump");
        }

        if (!ic.isKeyPressed("A") && !ic.isKeyPressed("D") && !ic.isKeyPressed("LEFT") && !ic.isKeyPressed("RIGHT") && !isJumping) {
            animationManager.stopMoving();  // Stop the walking animation, reverting to idle
            //Part that Paul is not clear on, detecting error
            // 😂😂😂
            isMoving = false;
            if(isJumping)
                animationManager.startMoving("jump");
        }
        if(rb.attribs.grounded) {
            isJumping = false;
            if (isMoving) {
                animationManager.startMoving("walk");
            }
        }

        if(isJumping)
            animationManager.startMoving("jump");
    }

    @Override
    public void pauseEvent(GameObject parent, boolean isPaused) {
        if (isPaused)
            animationManager.stopMoving();
    }

    @Override
    public void reset(GameObject parent) {
        enableAnimation = true;
    }

    private void move(GameObject ref, double xDelta, double yDelta) {
        ref.getScriptable(RigidBody2D.class).addForce(xDelta*500, yDelta*200);
    }

    private void jump(GameObject ref, double dt) {
        if(rb.attribs.grounded && rb.getVelocityY() >= 0){
            rb.attribs.grounded = false;
            move(ref, 0, -1500*jumpForce*(0.6d)/* * dt*20*/);
            
        }
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public float getJumpForce() {
        return jumpForce;
    }

    public void setJumpForce(float jumpForce) {
        this.jumpForce = jumpForce;
    }

    public boolean canMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean canJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    @Override
    public JSONObject serialize() {
        JSONObject output = new JSONObject();
        output.put("moveSpeed", moveSpeed);
        output.put("jumpForce", jumpForce);
        output.put("canMove", canMove);
        output.put("enableYMovement", canJump);
        return output;
    }

    @Override
    public void deserialize(JSONObject data) {
        moveSpeed = data.getFloat("moveSpeed");
        jumpForce = data.getFloat("jumpForce");
        canMove = data.getBoolean("canMove");
        canJump = data.getBoolean("enableYMovement");
    }
}
