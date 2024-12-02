package com.project.use_cases.core.prebuilts.scripts;

import com.project.entity.core.GameObject;
import com.project.entity.physics.physics_body.RigidBody2D;
import com.project.entity.scripting.IScriptable;
import com.project.entity.ui.GameUI;
import com.project.use_cases.core.prebuilts.ui.UIFactory;
import com.project.use_cases.player_move.PlayerMoveInteractor;

import java.util.Iterator;

public class LoadScript implements IScriptable {
    private final int MIN_HOLD_TIME = 20;
    private final double MIN_RUNNABLE_DELTA = 0.02;
    private int currHoldTime = 0;
    private int consistantFrameThreshold = 0;
    private int currConsistantFrames = 0;

    @Override
    public void start(GameObject parent) {
        Iterator<GameObject> objs = parent.getLinkedScene().getSceneObjects();
        while (objs.hasNext()) {
            GameObject obj = objs.next();

            RigidBody2D rb = obj.getScriptable(RigidBody2D.class, true);
            PlayerMoveInteractor mc = obj.getScriptable(PlayerMoveInteractor.class, true);

            if (rb != null) {
                obj.disableScript(rb);
                obj.disableScript(mc);
            }
        }
    }

    @Override
    public void update(GameObject parent, double deltaTime){
        if (currHoldTime == -1) {
            if (deltaTime > MIN_RUNNABLE_DELTA) {
                currHoldTime = 0;
                currConsistantFrames = 0;
            }
            else {
                currConsistantFrames++;

                if (currConsistantFrames >= consistantFrameThreshold) {
                    Iterator<GameObject> objs = parent.getLinkedScene().getSceneObjects();

                    while (objs.hasNext()) {
                        GameObject obj = objs.next();

                        RigidBody2D rb = obj.getScriptable(RigidBody2D.class, true);
                        PlayerMoveInteractor mc = obj.getScriptable(PlayerMoveInteractor.class, true);

                        obj.enableScript(rb);
                        obj.enableScript(mc);
                    }

                    parent.getLinkedScene().reset();
                    parent.getLinkedScene().removeSceneObject(parent);
                    parent.getLinkedScene().addUIElement(new GameUI(UIFactory.DeathLabelFactory(0, 10, 220, 70)));
                }
            }
        }
        else if (currHoldTime < MIN_HOLD_TIME) {
            currHoldTime++;
        }
        else {
            consistantFrameThreshold++;

            if (deltaTime < MIN_RUNNABLE_DELTA) {
                currHoldTime = -1;
                currConsistantFrames /= 2;
            }
        }
    }
}
