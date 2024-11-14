package com.project.engine.Physics.Collision;

import com.project.engine.Core.GameObject;
import com.project.engine.Physics.Collision.Types.ECollisionVolume;

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
