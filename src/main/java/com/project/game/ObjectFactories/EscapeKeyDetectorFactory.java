package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.game.Scenes.LevelSelectionFactory;
import com.project.game.Scripts.SceneExit;

public class EscapeKeyDetectorFactory extends AbstractObjectFactory {
    protected EscapeKeyDetectorFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        obj.addBehavior(new SceneExit());
        return obj;
    }
}
