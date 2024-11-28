package com.project.entity.physics.collision;

import com.project.entity.physics.collision.types.ECollisionVolume;
import com.project.entity.core.GameObject;

public class Collider extends CollisionVolume {
    public ECollisionVolume volumeType() { return ECollisionVolume.COLLIDER; }

    public Collider(){
        super();
        setTag("Collider");
    }

    public void onCollisionEnter(GameObject parent, GameObject other, CollisionVolume interactor) {}
    public void onCollisionExit(GameObject parent, GameObject other, CollisionVolume interactor) {}
    public void onCollisionContinue(GameObject parent, GameObject other, CollisionVolume interactor) {}
}
