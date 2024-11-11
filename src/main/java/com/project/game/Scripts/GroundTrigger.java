package com.project.game.Scripts;

import com.project.engine.Core.GameObject;
import com.project.engine.Rendering.IRenderable;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.IScriptable;
import com.project.physics.Collision.BoxTrigger;
import com.project.physics.Collision.CollisionVolume;
import com.project.physics.PhysicsBody.RigidBody2D;

import java.util.Iterator;

public class GroundTrigger extends BoxTrigger implements IScriptable {
    private int groundInteractCount = 0;
    public GroundTrigger(double xOff, double yOff, double widthOff, double heightOff) {
        setOffset(xOff, yOff);
        setRelDimensions(widthOff, heightOff);
    }

    @Override
    public void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
        RigidBody2D rb = parent.getScriptable(RigidBody2D.class);
        SimpleCollider thisCollider = parent.getScriptable(SimpleCollider.class);

        if (other.hasTag("ground") && rb.getVelocityY() >= 0 && thisCollider != null){
            groundInteractCount++;

            rb.resetY();
            rb.attribs.grounded = true;
            rb.attribs.groundFrictionCoefficient = other.getScriptable(GroundStats.class).getFriction();

            Iterator<IRenderable> playerModel = parent.getRenderables();

            while (playerModel.hasNext()) {
                SpriteRenderer renderable = (SpriteRenderer)playerModel.next();
                renderable.setImage("assets/character.png", 128, 128);
            }
        }
    }

    @Override
    public void onTriggerExit(GameObject parent, GameObject other, CollisionVolume interactor) {
        if (other.hasTag("ground")){
            groundInteractCount--;
            if (groundInteractCount == 0){
                parent.getScriptable(RigidBody2D.class).attribs.grounded = false;
            }
        }
    }
}
