package com.project.game.Scripts;

import com.project.engine.Core.GameObject;
import com.project.engine.Scripting.IScriptable;
import com.project.engine.Physics.Collision.BoxCollider;
import com.project.engine.Physics.Collision.CollisionVolume;
import org.json.JSONObject;

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
