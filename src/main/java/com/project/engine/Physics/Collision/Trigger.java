package com.project.engine.Physics.Collision;

import com.project.engine.Core.GameObject;
import com.project.engine.Physics.Collision.Types.ECollisionVolume;

public class Trigger extends CollisionVolume {
    public ECollisionVolume volumeType() { return ECollisionVolume.TRIGGER; }

    public Trigger() {
        super();
        setTag("Trigger");
    }

    public void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor) {}
    public void onTriggerExit(GameObject parent, GameObject other, CollisionVolume interactor) {}
    public void onTriggerContinue(GameObject parent, GameObject other, CollisionVolume interactor) {}
}
