package com.project.use_cases.play_prebuilt_levels.game_objects;

import com.project.entity.core.GameObject;
import com.project.entity.rendering.SpriteRenderer;

public class CloudFactory extends AbstractObjectFactory {
    protected CloudFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        SpriteRenderer cloudRenderer = new SpriteRenderer("assets/cloud.png", 128, 128);
        obj.addRenderable(cloudRenderer);
        return obj;
    }
}
