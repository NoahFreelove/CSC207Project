package com.project.entity.physics.collision;

import com.project.entity.physics.collision.types.ECollisionShape;

public class BoxTrigger extends Trigger {
    public ECollisionShape volumeShape() { return ECollisionShape.BOX; }
}
