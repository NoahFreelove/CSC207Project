package com.project.use_cases.player_move;

import com.project.entity.core.GameObject;
import com.project.entity.rendering.IRenderable;
import com.project.entity.rendering.SpriteRenderer;
import com.project.entity.scripting.IScriptable;
import com.project.entity.physics.collision.BoxTrigger;
import com.project.entity.physics.collision.CollisionVolume;
import com.project.entity.physics.physics_body.RigidBody2D;
import com.project.use_cases.core.prebuilts.scripts.GroundStats;
import com.project.use_cases.core.prebuilts.scripts.SimpleCollider;
import org.json.JSONObject;

import java.util.Iterator;

public class GroundTrigger extends BoxTrigger implements IScriptable {
    private int groundInteractCount = 0;
    private boolean triggerCoyoteTime = false;
    private final double COYOTE_TIME = 0.05;
    private double currCoyoteTime = 0;

    public GroundTrigger(double xOff, double yOff, double widthOff, double heightOff) {
        setOffset(xOff, yOff);
        setRelDimensions(widthOff, heightOff);
    }

    @Override
    public void update(GameObject parent, double deltaTime) {
        RigidBody2D rb = parent.getScriptable(RigidBody2D.class);

        if (rb == null) {
            return;
        }

        if (!rb.attribs.grounded) {
            groundInteractCount = 0;
        }

        if (triggerCoyoteTime) {
            currCoyoteTime += deltaTime;
            if (currCoyoteTime > COYOTE_TIME) {
                parent.getScriptable(RigidBody2D.class).attribs.grounded = false;
                triggerCoyoteTime = false;
            }
        }
        else{
            currCoyoteTime = 0;
        }
    }

    @Override
    public void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
        RigidBody2D rb = parent.getScriptable(RigidBody2D.class);
        SimpleCollider thisCollider = parent.getScriptable(SimpleCollider.class);

        if (other.hasTag("ground") && thisCollider != null){
            groundInteractCount++;
            triggerCoyoteTime = false;
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
        PlayerMoveInteractor mc = parent.getScriptable(PlayerMoveInteractor.class);
        if (mc == null)
            return;
        if(other.hasTag("speedupPlatform")){
            mc.speedupPlatform = true;
        }
        if(other.hasTag("ice")){
            mc.onIce = true;
        }
    }

    @Override
    public void onTriggerExit(GameObject parent, GameObject other, CollisionVolume interactor) {
        if (other.hasTag("ground")){
            groundInteractCount--;
            if (groundInteractCount == 0){
                triggerCoyoteTime = true;
            }
        }

        PlayerMoveInteractor mc = parent.getScriptable(PlayerMoveInteractor.class);
        if (mc == null)
            return;

        if(other.hasTag("speedupPlatform")){
            mc.speedupPlatform = false;
        }
        if(other.hasTag("ice")){
            mc.onIce = false;
        }
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
