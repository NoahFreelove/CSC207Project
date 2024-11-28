package com.project.use_cases.play_prebuilt_levels.scripts;

import com.project.entity.core.GameObject;
import com.project.entity.physics.collision.CollisionVolume;
import com.project.entity.scripting.IScriptable;
import com.project.use_cases.play_prebuilt_levels.scenes.WinOverlayFactory;

public class WinScript extends SimpleTrigger implements IScriptable {
    public boolean overrideWinEvent = false;
    public Runnable r = null;
    @Override
    public void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
        if (other.hasTag("player")) {
            if(!overrideWinEvent)
                WinOverlayFactory.winGame();
            else {
                if (r != null)
                    r.run();
            }
        }
    }
}