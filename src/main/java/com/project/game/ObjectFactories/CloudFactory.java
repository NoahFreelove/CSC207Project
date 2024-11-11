package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.engine.Rendering.SpriteRenderer;

import javax.naming.spi.ObjectFactory;

public class CloudFactory extends AbstractObjectFactory {
    protected CloudFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        SpriteRenderer cloudRenderer = new SpriteRenderer("assets/CSC207_asset_cloud.png", 128, 128);
        obj.addRenderable(cloudRenderer);
        return obj;
    }
}
