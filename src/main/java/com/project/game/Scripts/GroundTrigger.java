package com.project.game.Scripts;

import com.project.engine.Core.GameObject;
import com.project.engine.Rendering.IRenderable;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.IScriptable;
import com.project.engine.Physics.Collision.BoxTrigger;
import com.project.engine.Physics.Collision.CollisionVolume;
import com.project.engine.Physics.PhysicsBody.RigidBody2D;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.json.JSONObject;

import java.util.Iterator;

public class GroundTrigger extends BoxTrigger implements IScriptable {
    private int groundInteractCount = 0;
    public GroundTrigger(double xOff, double yOff, double widthOff, double heightOff) {
        setOffset(xOff, yOff);
        setRelDimensions(widthOff, heightOff);
    }

    @Override
    public void update(GameObject parent, double deltaTime) {
        RigidBody2D rb = parent.getScriptable(RigidBody2D.class);

        if (!rb.attribs.grounded) {
            groundInteractCount = 0;
        }
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
    public void onTriggerContinue(GameObject parent, GameObject other, CollisionVolume interactor) {
        MovementController mc = parent.getScriptable(MovementController.class);
        if (mc == null)
            return;
        if(other.hasTag("speedupPlatform")){
            mc.speedupPlatform = true;
        }
        else{
            mc.speedupPlatform = false;
        }
        if(other.hasTag("ice")){
            mc.onIce = true;
        }
        else{
            mc.onIce = false;
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

     /*   MovementController mc = parent.getScriptable(MovementController.class);
        if (mc == null)
            return;

        if(other.hasTag("speedupPlatform")){
            mc.speedupPlatform = false;
        }
        if(other.hasTag("ice")){
            mc.onIce = false;
        }*/
    }

    @Override
    public JSONObject serialize() {
        JSONObject out = new JSONObject();
        out.put("relX", getRelWidth());
        out.put("relY", getRelHeight());
        out.put("offX", getXOffset());
        out.put("offY", getYOffset());
        return out;
    }

    @Override
    public void deserialize(JSONObject data) {
        setRelDimensions(data.getDouble("relX"), data.getDouble("relY"));
        setOffset(data.getDouble("offX"), data.getDouble("offY"));
    }
}
