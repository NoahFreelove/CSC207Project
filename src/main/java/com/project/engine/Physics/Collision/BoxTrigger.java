package com.project.engine.Physics.Collision;

import com.project.engine.Physics.Collision.Types.ECollisionShape;

public class BoxTrigger extends Trigger {
    public ECollisionShape volumeShape() { return ECollisionShape.BOX; }
}
