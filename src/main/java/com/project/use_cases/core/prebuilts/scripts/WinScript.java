package com.project.use_cases.core.prebuilts.scripts;

import com.project.entity.core.GameObject;
import com.project.entity.physics.collision.CollisionVolume;
import com.project.entity.scripting.IScriptable;
import com.project.use_cases.game_win.GameWinInteractor;

public class WinScript extends SimpleTrigger implements IScriptable {
    public boolean overrideWinEvent = false;
    public Runnable r = null;
    private boolean won = false;

    @Override
    public void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
        if (other.hasTag("player") && !won) {
            won = true;
            if (!overrideWinEvent) {
                GameWinInteractor.execute(parent.getLinkedScene());
            }
            else {
                if (r != null)
                    r.run();
            }
        }
    }
}
