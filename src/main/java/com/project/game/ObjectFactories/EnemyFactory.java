package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.engine.Physics.PhysicsBody.RigidBody2D;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.WindowStatsDebug;
import com.project.game.Scripts.*;

public class EnemyFactory extends AbstractObjectFactory {
    protected EnemyFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        obj.addTag("enemy");

        SpriteRenderer sr = new SpriteRenderer("assets/enemy.png", 64,64);
        sr.setIndependentOfCamera(false);
        sr.setEnabled(true);
        obj.addRenderable(sr);

        obj.addBehavior(new SpawnPoint(x, y));
        obj.addBehavior(new EnemyController());

        RigidBody2D rb = new RigidBody2D();
        rb.attribs.maxVelocityX = 100;

        obj.addBehavior(rb);

        return obj;
    }
}
