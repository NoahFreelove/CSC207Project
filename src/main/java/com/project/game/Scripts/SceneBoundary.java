package com.project.game.Scripts;

import com.project.engine.Core.GameObject;
import com.project.engine.Scripting.IScriptable;
import com.project.engine.Physics.Collision.BoxTrigger;
import com.project.engine.Physics.Collision.CollisionVolume;

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
}
