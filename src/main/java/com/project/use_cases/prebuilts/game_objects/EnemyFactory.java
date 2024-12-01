package com.project.use_cases.prebuilts.game_objects;

import com.project.entity.core.GameObject;
import com.project.entity.physics.physics_body.RigidBody2D;
import com.project.entity.rendering.SpriteRenderer;
import com.project.use_cases.play_prebuilt_levels.scripts.SpawnPoint;
import com.project.use_cases.play_prebuilt_levels.scripts.EnemyController;

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
