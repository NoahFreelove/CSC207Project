package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.WindowStatsDebug;
import com.project.game.Scripts.*;
import com.project.physics.PhysicsBody.RigidBody2D;

public class PlayerFactory extends AbstractObjectFactory {
    protected PlayerFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        obj.addTag("player");

        SpriteRenderer sr = new SpriteRenderer("assets/character.png", 128,128);
        sr.setIndependentOfCamera(false);
        obj.addRenderable(sr);

        obj.addBehavior(new SpawnPoint(300, 480));
        obj.addBehavior(new DeathJoke());
        obj.addBehavior(new MovementController());
        obj.addBehavior(new RigidBody2D());
        obj.addBehavior(new SimpleCollider(32, 19.2, 0.5, 0.7));
        obj.addBehavior(new GroundTrigger(32,103, 0.5, 0.05));
        obj.addBehavior(new WindowStatsDebug());

        // add head trigger
        SimpleTrigger st = new SimpleTrigger();
        st.setTag("playerHead");

        st.setOffset(0, -1);
        st.setRelDimensions(1, 0.2f);
        obj.addBehavior(st);

        return obj;
    }
}
