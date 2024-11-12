package com.project.game.Scripts;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.Input.EInputType;
import com.project.engine.Rendering.IRenderable;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.IScriptable;
import com.project.physics.PhysicsBody.RigidBody2D;
import entity.Animation;
import entity.AnimationManager;
import entity.WalkAnimation;
import org.json.JSONObject;

import javax.imageio.event.IIOReadProgressListener;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

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
    public void onInput(GameObject parent, String keyName, EInputType inputType, int inputMods) {
        GameWindow win = Engine.getInstance().getPrimaryWindow();

        if (keyName.equals("A") && inputType == EInputType.PRESS) {
            startMoving(parent, true);
        }
        // Check if the "A" key is released
        else if (keyName.equals("A") && inputType == EInputType.RELEASE) {
            stopMoving();
        }

    }

    private void startMoving(GameObject parent, boolean isLeft) {
        isMoving = true;
        if (isLeft) {
            parent.getTransform().faceLeft();
        } else {
            parent.getTransform().faceRight();
        }
        animationManager.startAnimation("walk");

        // Start a timer to update the animation manager periodically
        if (animationTimer == null) {
            animationTimer = new Timer();
            animationTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    animationManager.update();
                }
            }, 0, 1000 / 30); // Run at ~30 FPS
        }

    }

    private void stopMoving() {
        isMoving = false;
        animationManager.endAnimation();

        // Cancel the timer to stop the animation loop
        if (animationTimer != null) {
            animationTimer.cancel();
            animationTimer = null;
        }
    }


    @Override
    public void update(GameObject parent, double deltaTime) {
        animationManager = new AnimationManager((SpriteRenderer) parent.getRenderables().next(), 128, 128);
        animationManager.addAnimation("walk", new WalkAnimation());
        GameWindow win = Engine.getInstance().getPrimaryWindow();
        if (win == null || rb == null) {
            return;
        }

        boolean isMoving = false;

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

        if (win.isKeyPressed("SPACE") && canJump) {
            jump(parent);
        }

        // Display the current frame
     /*   String currentFrame = Animation.getCurrentFrame();
        if (currentFrame != null) {  // If the current frame is not null
            ((SpriteRenderer)parent.getRenderables().next()).setImage(currentFrame, 128, 128);
        } */


        animationManager.setPosition(128, 128);


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
