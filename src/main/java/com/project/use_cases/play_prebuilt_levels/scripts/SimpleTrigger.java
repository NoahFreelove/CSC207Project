package com.project.use_cases.play_prebuilt_levels.scripts;

import com.project.entity.core.GameObject;
import com.project.entity.scripting.ILambdaTrigger;
import com.project.entity.scripting.IScriptable;
import com.project.entity.physics.collision.BoxTrigger;
import com.project.entity.physics.collision.CollisionVolume;
import org.json.JSONObject;

public class SimpleTrigger extends BoxTrigger implements IScriptable {
    private final ILambdaTrigger trigger;

    public SimpleTrigger() {
        trigger = new ILambdaTrigger() {};
    }

    public SimpleTrigger(ILambdaTrigger trigger) {
        this.trigger = trigger;
    }

    @Override
    public void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
        trigger.onTriggerEnter(parent, other, interactor);
    }

    @Override
    public void onTriggerExit(GameObject parent, GameObject other, CollisionVolume interactor) {
        trigger.onTriggerExit(parent, other, interactor);
    }

    @Override
    public void onTriggerContinue(GameObject parent, GameObject other, CollisionVolume interactor) {
        trigger.onTriggerContinue(parent, other, interactor);
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

    @Override
    public void reset(GameObject parent) {
        trigger.onReset();
    }
}
