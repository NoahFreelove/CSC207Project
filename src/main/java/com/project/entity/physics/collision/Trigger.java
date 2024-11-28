package com.project.entity.physics.collision;

import com.project.entity.physics.collision.types.ECollisionVolume;
import com.project.entity.core.GameObject;

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
