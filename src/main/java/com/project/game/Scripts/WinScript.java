package com.project.game.Scripts;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Physics.Collision.CollisionVolume;
import com.project.engine.Scripting.IScriptable;
import com.project.game.Scenes.WinOverlayFactory;

import java.util.Iterator;

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
