package com.project.engine.Scripting;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.Input.EInputType;

public class MovementController implements IScriptable {

    private float moveSpeed = 2f;
    private float jumpForce = 10f;

    private boolean canMove = true;

    private boolean enableYMovement = true;

    public MovementController() {

    }

    @Override
    public void onInput(GameObject parent, String keyName, EInputType inputType, int inputMods) {
        System.out.println(keyName);
        if (inputType == EInputType.RELEASE && "1".equals(keyName)) {
            moveSpeed++;
        }
        else if (inputType == EInputType.RELEASE && "2".equals(keyName)) {
            moveSpeed--;
        }
        else if (inputType == EInputType.RELEASE && "ESC".equals(keyName)) {
            Engine.getInstance().exitEngine();
        }
    }

    @Override
    public void update(GameObject parent, double deltaTime) {
        GameWindow win = Engine.getInstance().getPrimaryWindow();
        if (win == null) {
            return;
        }

        double actualSpeed = moveSpeed * deltaTime * 300;

        if (enableYMovement) {
            if (win.isKeyPressed("W") || win.isKeyPressed("UP")) {
                parent.getTransform().setScaleX(2);
                parent.getTransform().setScaleY(3);
                parent.getTransform().faceRight();
                parent.getTransform().setRotation(270);
                move(parent, 0, -actualSpeed);
            }

            if (win.isKeyPressed("S") || win.isKeyPressed("DOWN")) {
                parent.getTransform().setScaleX(4);
                parent.getTransform().setScaleY(2);
                parent.getTransform().faceRight();
                parent.getTransform().setRotation(90);
                move(parent, 0, actualSpeed);
            }
        }

        if (win.isKeyPressed("A") || win.isKeyPressed("LEFT")) {
            parent.getTransform().setScaleX(2);
            parent.getTransform().setScaleY(1);
            parent.getTransform().faceLeft();
            parent.getTransform().setRotation(0);
            move(parent, -actualSpeed, 0);
        }

        if (win.isKeyPressed("D") || win.isKeyPressed("RIGHT")) {
            parent.getTransform().setScaleX(2);
            parent.getTransform().setScaleY(1);
            parent.getTransform().faceRight();
            parent.getTransform().setRotation(0);
            move(parent, actualSpeed, 0);
        }

        if (win.isKeyPressed("SPACE")) {
            jump();
        }
    }

    private void move(GameObject ref, double xDelta, double yDelta) {
        if (!canMove) {
            return;
        }

        ref.getTransform().translate(xDelta, yDelta);
    }

    private void jump() {

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

    public boolean canMoveY() {
        return enableYMovement;
    }

    public void setCanMoveY(boolean enableYMovement) {
        this.enableYMovement = enableYMovement;
    }
}