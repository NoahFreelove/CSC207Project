package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.game.Scripts.SceneBoundary;
import com.project.game.Scripts.SimpleCollider;

public class BoundaryFactory extends AbstractObjectFactory {
    protected BoundaryFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);

        SimpleCollider sc = new SimpleCollider();
        obj.addBehavior(sc);

        SceneBoundary boundary = new SceneBoundary();
        obj.addBehavior(boundary);

        return obj;
    }
}
