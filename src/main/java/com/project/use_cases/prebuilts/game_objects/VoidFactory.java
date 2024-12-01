package com.project.use_cases.prebuilts.game_objects;

import com.project.entity.core.GameObject;
import com.project.use_cases.play_prebuilt_levels.scripts.SimpleTrigger;
import com.project.use_cases.play_prebuilt_levels.scripts.VoidScript;

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
