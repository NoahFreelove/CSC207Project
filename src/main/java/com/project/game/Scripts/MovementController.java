package com.project.game.Scripts;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.Rendering.IRenderable;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.IScriptable;
import com.project.engine.Physics.PhysicsBody.RigidBody2D;
import entity.AnimationManager;
import entity.WalkAnimation;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Timer;

public class MovementController implements IScriptable {

    private float moveSpeed = 8f;
    private float jumpForce = 1.15f;

    private boolean canMove = true;

    private boolean canJump = true;

    private RigidBody2D rb = null;

    private AnimationManager animationManager;

    private boolean isMoving = false;
    private Timer animationTimer;
    public MovementController() {

    }

    @Override
    public void start(GameObject parent) {
        rb = parent.getScriptable(RigidBody2D.class);
        animationManager = new AnimationManager((SpriteRenderer) parent.getRenderables().next(), 128, 128);
        animationManager.addAnimation("walk", new WalkAnimation());
    }

    @Override
    public void update(GameObject parent, double deltaTime) {
        GameWindow win = Engine.getInstance().getPrimaryWindow();
        if (win == null)
            return;

        if (canMove) {
            double actualSpeed = moveSpeed * deltaTime * 300;


            if (win.isKeyPressed("A") || win.isKeyPressed("LEFT")) {
                parent.getTransform().faceLeft();
                parent.getTransform().setRotation(0);
                move(parent, -actualSpeed, 0);

            }

            if (win.isKeyPressed("D") || win.isKeyPressed("RIGHT")) {
                parent.getTransform().faceRight();
                parent.getTransform().setRotation(0);
                move(parent, actualSpeed, 0);
            }
        }

        if ((win.isKeyPressed("SPACE") || win.isKeyPressed("W") || win.isKeyPressed("UP"))  && canJump) {
            jump(parent);
        }

        if (animationManager == null)
            return;

        if (win.isKeyPressed("A") || win.isKeyPressed("LEFT") || win.isKeyPressed("D") || win.isKeyPressed("RIGHT")) {
            if (!isMoving) {
                animationManager.startMoving("walk");  // Start walking animation if not already moving
                isMoving = true;
            }

        }

        if (!win.isKeyPressed("A") && !win.isKeyPressed("D") && !win.isKeyPressed("LEFT") && !win.isKeyPressed("RIGHT")) {
            animationManager.stopMoving();  // Stop the walking animation, reverting to idle
            //Part that Paul is not clear on, detecting error
            // 😂😂😂
            isMoving = false;
        }

    }

    private void move(GameObject ref, double xDelta, double yDelta) {
        ref.getScriptable(RigidBody2D.class).addForce(xDelta*500, yDelta*200);
    }

    private void jump(GameObject ref) {
        if(rb.attribs.grounded && rb.getVelocityY() >= 0){
            rb.attribs.grounded = false;
            move(ref, 0, -1500*jumpForce);
            Iterator<IRenderable> playerModel = ref.getRenderables();

            while (playerModel.hasNext()) {
                SpriteRenderer renderable = (SpriteRenderer)playerModel.next();
                renderable.setImage("assets/char_jump_straight.png", 128, 128);
            }
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
