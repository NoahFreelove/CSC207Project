package com.project.game.ObjectFactories;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Scripting.ILambdaTrigger;
import com.project.engine.Scripting.IScriptable;
import com.project.game.Scripts.PlayerDeath;
import com.project.game.Scripts.SimpleTrigger;
import com.project.game.Scripts.VoidScript;
import com.project.physics.Collision.CollisionVolume;

import java.util.Iterator;

public class VoidFactory extends AbstractObjectFactory {
    protected VoidFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        obj.addTag("void");

        obj.getTransform().setScale(0, 0);
        SimpleTrigger deathCollider = new VoidScript();
        obj.addBehavior(deathCollider);

        return obj;
    }
}
