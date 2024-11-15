package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.engine.Rendering.SpriteRenderer;

public class BackgroundFactory extends AbstractObjectFactory {
    protected BackgroundFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z,  double width, double height) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);

        SpriteRenderer sr = new SpriteRenderer("assets/bg.png", 800,800);
        sr.setIndependentOfCamera(true);
        obj.addRenderable(sr);

        return obj;
    }
}
