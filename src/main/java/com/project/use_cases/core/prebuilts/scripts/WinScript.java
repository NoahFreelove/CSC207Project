package com.project.use_cases.core.prebuilts.scripts;

import com.project.entity.core.GameObject;
import com.project.entity.physics.collision.CollisionVolume;
import com.project.entity.scripting.IScriptable;
import com.project.use_cases.core.prebuilts.scenes.WinOverlayFactory;

public class WinScript extends SimpleTrigger implements IScriptable {
    public boolean overrideWinEvent = false;
    public Runnable r = null;
    public static boolean winGame = false;
    @Override
    public void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
        if (other.hasTag("player")) {
            if (!overrideWinEvent) {
                winGame = true;
                WinOverlayFactory.winGame();
            }
            else {
                if (r != null)
                    r.run();
            }
        }
    }

    public static boolean getGameStatus() {
        return winGame;
    }

    public static void restartGameStatus() {
        winGame = false;
    }
}
