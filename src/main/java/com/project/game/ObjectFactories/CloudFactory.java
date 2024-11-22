package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.engine.Physics.Collision.CollisionVolume;
import com.project.engine.Physics.Collision.Types.ECollisionVolume;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.ILambdaTrigger;
import com.project.game.Scripts.PlayerDeath;
import com.project.game.Scripts.SimpleTrigger;

public class CloudFactory extends AbstractObjectFactory {
    protected CloudFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        SpriteRenderer cloudRenderer = new SpriteRenderer("assets/cloud.png", 128, 128);
        obj.addRenderable(cloudRenderer);
        return obj;
    }
}
