package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.WindowStatsDebug;
import com.project.game.Scripts.*;
import com.project.engine.Physics.PhysicsBody.RigidBody2D;

public class PlayerFactory extends AbstractObjectFactory {
    protected PlayerFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        obj.addTag("player");

        float factor = 1.5f;
        float tmp = 128/factor;

        SpriteRenderer sr = new SpriteRenderer("assets/character.png", (int)(tmp),(int)(tmp));
        sr.setIndependentOfCamera(false);
        obj.addRenderable(sr);

        obj.addBehavior(new SpawnPoint(x, y));
        obj.addBehavior(new PlayerDeath());
        obj.addBehavior(new DeathJoke());
        obj.addBehavior(new MovementController());
        obj.addBehavior(new RigidBody2D());
        SimpleCollider sc = new SimpleCollider(tmp/8, tmp*0.15, 0.5, 0.7);
        obj.addBehavior(sc);
        obj.addBehavior(new GroundTrigger(tmp/8,tmp*0.8, 0.5, 0.05));
        obj.addBehavior(new WindowStatsDebug());

        // add head trigger
        SimpleTrigger st = new SimpleTrigger();
        st.setTag("playerHead");

        st.setOffset(0, -1/2f);
        st.setRelDimensions(1, 0.2f/2f);
        obj.addBehavior(st);

        return obj;
    }
}
