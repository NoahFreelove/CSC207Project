package com.project.use_cases.play_prebuilt_levels.game_objects;

import com.project.entity.core.GameObject;
import com.project.entity.rendering.SpriteRenderer;
import com.project.use_cases.play_prebuilt_levels.scripts.GroundStats;
import com.project.use_cases.play_prebuilt_levels.scripts.SimpleCollider;

public class IceBlockFactory extends AbstractObjectFactory {
    private final String DEFAULT_ICE_ASSET = "assets/ice.png";

    protected IceBlockFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        return produceGameObject(x, y, z, width, height, 0.0, DEFAULT_ICE_ASSET);
    }

    protected GameObject produceGameObject(double x, double y, int z, double width, double height, double friction, String asset) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        obj.addTag("ground");
        obj.addTag("ice");
        obj.getTransform().update(obj, 0); // This call allows obj to have right tiling scale.
        SpriteRenderer sr = new SpriteRenderer(asset, 64,64);
        sr.setTile(true);
        sr.setTileX(obj.getTransform().getScaleX());
        sr.setTileY(obj.getTransform().getScaleY());
        obj.addRenderable(sr);


        obj.addBehavior(new GroundStats(friction));
        obj.addBehavior(new SimpleCollider());

        return obj;
    }
}
