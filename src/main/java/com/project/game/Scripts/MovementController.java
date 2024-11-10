package com.project.game.Scripts;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.Input.EInputType;
import com.project.engine.Scripting.IScriptable;
import com.project.physics.PhysicsBody.RigidBody2D;
import org.json.JSONObject;

public class MovementController implements IScriptable {

    private float moveSpeed = 8f;
    private float jumpForce = 1.15f;

    private boolean canMove = true;

    private boolean canJump = true;

    private RigidBody2D rb = null;

    public MovementController() {

    }

    @Override
    public void start(GameObject parent) {
        rb = parent.getScriptable(RigidBody2D.class);
    }

    @Override
    public void onInput(GameObject parent, String keyName, EInputType inputType, int inputMods) {
        //System.out.println(keyName);
        if (inputType == EInputType.RELEASE && "1".equals(keyName)) {
            moveSpeed++;
        }
        else if (inputType == EInputType.RELEASE && "2".equals(keyName)) {
            moveSpeed--;
        }
    }

    @Override
    public void update(GameObject parent, double deltaTime) {
        GameWindow win = Engine.getInstance().getPrimaryWindow();
        if (win == null || rb == null) {
            return;
        }

        //System.out.println(win.FPS());

        if(canMove) {
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
    }

    private void move(GameObject ref, double xDelta, double yDelta) {
        ref.getScriptable(RigidBody2D.class).addForce(xDelta*500, yDelta*200);
    }

    private void jump(GameObject ref) {
        if(rb.attribs.grounded && rb.getVelocityY() >= 0){
            rb.attribs.grounded = false;
            move(ref, 0, -1500*jumpForce);
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
