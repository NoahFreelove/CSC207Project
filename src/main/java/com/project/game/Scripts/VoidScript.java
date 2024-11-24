package com.project.game.Scripts;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Scripting.IScriptable;
import com.project.engine.Physics.Collision.CollisionVolume;

import java.util.Iterator;

public class VoidScript extends SimpleTrigger implements IScriptable {
    private int updateFramesUntilVoid = 3; //Void gets generated 3 frames in
    private final int X_BUFFER = 300; //Extends void beyond object placement for safety
    @Override
    public void update(GameObject parent, double deltaTime) {
        if (updateFramesUntilVoid > 0) {
            updateFramesUntilVoid -= 1;
        }
        else if (updateFramesUntilVoid < 0) {
            return;
        }

        updateFramesUntilVoid -= 1;

        double voidY = Engine.getInstance().getPrimaryWindow().getWindowSize().getSecond();
        double voidXmin = 0, voidXmax = 0;

        Iterator<GameObject> objectIterator = parent.getLinkedScene().getSceneObjects();

        while (objectIterator.hasNext()) {
            GameObject object = objectIterator.next();
            voidXmin = Math.min(voidXmin, object.getTransform().getPositionX());
            voidXmax = Math.max(voidXmax, object.getTransform().getPositionX() + object.getTransform().getWidth());
        }

        parent.getTransform().setPosition(voidXmin - X_BUFFER, voidY - 50);
        parent.getTransform().setWidth((int)(voidXmax - voidXmin + X_BUFFER * 2));
        parent.getTransform().setHeight(50);
        parent.getTransform().setScale(1, 1);
    }

    @Override
    public void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
        if(other.hasTag("player")) {
            PlayerDeath pd = other.getScriptable(PlayerDeath.class);
            if (pd != null) {
                pd.queueDeath();
            }
            else {
                System.err.println("No spawn point found");
            }
        }
    }
}
