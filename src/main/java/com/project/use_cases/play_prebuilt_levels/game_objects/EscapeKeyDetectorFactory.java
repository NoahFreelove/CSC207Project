package com.project.use_cases.play_prebuilt_levels.game_objects;

import com.project.entity.core.GameObject;
import com.project.use_cases.play_prebuilt_levels.scripts.SceneExit;

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
