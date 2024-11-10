package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.WindowStatsDebug;
import com.project.game.Scripts.*;
import com.project.physics.PhysicsBody.RigidBody2D;

public class PlayerFactory {

    public static GameObject createPlayer() {
        GameObject o = new GameObject();
        o.addTag("player");
        SpriteRenderer sr = new SpriteRenderer("assets/character.png", 128,128);
        sr.setIndependentOfCamera(false);
        o.getTransform().setPosition(0,0);
        o.getTransform().setZIndex(2);
        o.addBehavior(new SpawnPoint(300, 480));
        o.addBehavior(new DeathJoke());
        o.addBehavior(new MovementController());
        o.addBehavior(new RigidBody2D());
        o.addBehavior(new SimpleCollider(32, 19.2, 0.5, 0.7));
        o.addBehavior(new GroundTrigger(32, 99, 0.5, 0.1));
        o.addBehavior(new WindowStatsDebug());
        // add head trigger
        SimpleTrigger st = new SimpleTrigger();
        st.setTag("playerHead");
        st.setOffset(0, -1);
        st.setRelDimensions(1, 0.2f);
        o.addBehavior(st);
        o.addRenderable(sr);
        return o;
    }
}
