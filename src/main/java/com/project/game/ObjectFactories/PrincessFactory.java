package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.engine.Physics.PhysicsBody.RigidBody2D;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.game.Scripts.*;

public class PrincessFactory extends AbstractObjectFactory {
    protected PrincessFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        obj.addTag("princess");

        SpriteRenderer sr = new SpriteRenderer("assets/princess.png", 96,96);
        obj.getTransform().faceLeft();
        obj.addRenderable(sr);
        SimpleTrigger trigger = new WinScript();
        obj.addBehavior(trigger);

        return obj;
    }
}
