package com.project.use_cases.play_prebuilt_levels.scripts;

import com.project.entity.core.GameObject;
import com.project.entity.physics.physics_body.RigidBody2D;
import com.project.entity.scripting.IScriptable;

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

            RigidBody2D rb = obj.getScriptable(RigidBody2D.class);

            if (rb != null) {
                obj.disableScript(rb);
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

                        if (rb != null) {
                            obj.enableScript(rb);
                        }
                    }
                    parent.getLinkedScene().reset();
                    parent.getLinkedScene().removeSceneObject(parent);
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
