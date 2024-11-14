package com.project.game.Scripts;

import com.project.engine.Core.GameObject;
import com.project.engine.Scripting.IScriptable;
import com.project.engine.Physics.Collision.BoxCollider;
import com.project.engine.Physics.Collision.CollisionVolume;

public class SimpleCollider extends BoxCollider implements IScriptable {
    public SimpleCollider(double xOffset, double yOffset, double width, double height) {
        this.setOffset(xOffset, yOffset);
        this.setRelDimensions(width, height);
    }

    public SimpleCollider() {
    }

    @Override
    public void onCollisionEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
        super.onCollisionEnter(parent, other, interactor);
    }

    @Override
    public void onCollisionExit(GameObject parent, GameObject other, CollisionVolume interactor) {
        super.onCollisionExit(parent, other, interactor);
    }
}
