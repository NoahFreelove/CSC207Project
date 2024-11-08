package com.project.physics.Collision;

import com.project.physics.Collision.Types.ECollisionShape;

public class BoxTrigger extends Trigger {
    public ECollisionShape volumeShape() { return ECollisionShape.BOX; }
}
