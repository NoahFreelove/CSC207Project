package com.project.use_cases.core.prebuilts.game_objects;

import com.project.entity.core.GameObject;
import com.project.entity.rendering.SpriteRenderer;
import com.project.use_cases.core.prebuilts.scripts.LoadScript;

public class LoadScreenFactory extends AbstractObjectFactory {
    protected LoadScreenFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        SpriteRenderer sr = new SpriteRenderer("assets/loading.png", 800, 800);
        sr.setIndependentOfCamera(true);
        obj.addRenderable(sr);
        obj.addBehavior(new LoadScript());
        return obj;
    }
}
