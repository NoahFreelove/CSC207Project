package com.project.physics.Collision;

import com.project.engine.Core.GameObject;
import com.project.physics.Collision.Types.ECollisionVolume;

public class Trigger extends CollisionVolume {

    public ECollisionVolume volumeType() { return ECollisionVolume.TRIGGER; }

    public void onTriggerEnter(GameObject parent, GameObject other) {}
    public void onTriggerExit(GameObject parent, GameObject other) {}
    public void onTriggerContinue(GameObject parent, GameObject other) {}
}
