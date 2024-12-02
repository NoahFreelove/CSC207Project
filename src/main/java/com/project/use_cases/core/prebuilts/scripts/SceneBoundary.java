package com.project.use_cases.core.prebuilts.scripts;

import com.project.entity.core.GameObject;
import com.project.entity.scripting.IScriptable;
import com.project.entity.physics.collision.BoxTrigger;
import com.project.entity.physics.collision.CollisionVolume;
import org.json.JSONObject;

public class SceneBoundary extends BoxTrigger implements IScriptable {

    public void start(GameObject parent) {
        setRelDimensions(-parent.getLinkedScene().getCamera().getOffsetX() / parent.getTransform().getWidth(), 1); // Test left boundary
    }

    @Override
    public void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor) {

        // Prevent scene from displaying unintended area
        // Usw player head due to wider collision area
        if (other.hasTag("player")  && interactor.getTag().equals("playerHead")) {
            parent.getLinkedScene().getCamera().setFollowX(false);
            parent.getLinkedScene().getCamera().setOffsetX(-100);
        }
    }

    @Override
    public void onTriggerExit(GameObject parent, GameObject other, CollisionVolume interactor) {
        if (other.hasTag("player") && interactor.getTag().equals("playerHead")) {
            parent.getLinkedScene().getCamera().setFollowX(true);

            double playerX = other.getTransform().getPositionX();
            double cameraX = parent.getTransform().getPositionX();
            parent.getLinkedScene().getCamera().setOffsetX(cameraX - playerX);
        }
    }

    @Override
    public JSONObject serialize() {
        return new JSONObject();
    }
}
