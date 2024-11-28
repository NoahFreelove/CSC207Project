package com.project.use_cases.play_prebuilt_levels.game_objects;

import com.project.entity.core.GameObject;
import com.project.entity.rendering.SpriteRenderer;
import com.project.use_cases.play_prebuilt_levels.scripts.SimpleTrigger;
import com.project.use_cases.play_prebuilt_levels.scripts.WinScript;

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
