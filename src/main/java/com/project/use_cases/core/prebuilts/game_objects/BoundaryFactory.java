package com.project.use_cases.core.prebuilts.game_objects;

import com.project.entity.core.GameObject;
import com.project.use_cases.core.prebuilts.scripts.SceneBoundary;
import com.project.use_cases.core.prebuilts.scripts.SimpleCollider;

public class BoundaryFactory extends AbstractObjectFactory {
    protected BoundaryFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        obj.getTransform().update(obj, 0);
        SimpleCollider sc = new SimpleCollider();
        obj.addBehavior(sc);

        SceneBoundary boundary = new SceneBoundary();
        obj.addBehavior(boundary);

        return obj;
    }
}
