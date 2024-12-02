package com.project.use_cases.core.prebuilts.scripts;

import com.project.use_cases.core.game.GameInteractor;
import com.project.entity.core.GameObject;
import com.project.entity.scripting.IScriptable;
import com.project.entity.physics.collision.CollisionVolume;
import com.project.use_cases.player_death_count.PlayerDeathInteractor;

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

        double voidY = GameInteractor.getInstance().getPrimaryWindow().getWindowSize().getSecond();
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
            PlayerDeathInteractor pd = other.getScriptable(PlayerDeathInteractor.class);
            if (pd != null) {
                pd.execute();
            }
            else {
                System.err.println("No spawn point found");
            }
        }
    }
}
