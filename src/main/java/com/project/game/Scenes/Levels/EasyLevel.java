package com.project.game.Scenes.Levels;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Window.GameWindow;
import com.project.game.ObjectFactories.*;
import com.project.game.Scenes.LevelSelectionFactory;
import com.project.game.Scripts.SceneExit;

/**
 * A simple Level for Game PoC
 */
public class EasyLevel {
    private static Scene createScene() {
        Engine e = Engine.getInstance();
        GameWindow w = e.getPrimaryWindow();

        if (w == null) {
            System.out.println("Failed to obtain primary window");
            e.exitEngine();
            return null;
        }

        while (!w.isReady()) {}

        //String serialized = FileIO.ReadText("tmp/serialized_scene.json");
        //w.setActiveScene(SerializeManager.deserialize(serialized));
        Scene s = new Scene("Test Scene");

        GameObject exitInputLIstener = AbstractObjectFactory.generateOfType(ObjectType.ESCAPE_KEY);
        exitInputLIstener.getScriptable(SceneExit.class).setOnExit(LevelSelectionFactory::loadLevelSelection);
        s.addSceneObject(exitInputLIstener, true);

        s.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.BACKGROUND));

        GameObject player = AbstractObjectFactory.generateOfType(ObjectType.PLAYER, 2);

        s.getCamera().update(player, 0);
        s.getCamera().setOffsetX(-100);
        s.getCamera().setFollowY(false);

        s.addSceneObject(player, true);

        GroundBlockFactory groundMaker = (GroundBlockFactory) AbstractObjectFactory.makeFactory(ObjectType.GROUND_BLOCK);
        HiddenBlockFactory hiddenBlockMaker = (HiddenBlockFactory) AbstractObjectFactory.makeFactory(ObjectType.HIDDEN_BLOCK);
        CloudFactory cloudMaker = (CloudFactory) AbstractObjectFactory.makeFactory(ObjectType.CLOUD);

        s.addSceneObjects(
                groundMaker.generate(0, 600, 1, 10, 2),
                groundMaker.generate(1600, 600, 1, 10, 2),
                groundMaker.generate(500, 520, 0, 1, 1, "assets/brick.png"),
                groundMaker.generate(700, 400, 0, 1, 0.5, 1, "assets/brick.png"),
                hiddenBlockMaker.generate(1280, 380, 1),
                hiddenBlockMaker.generate(1280-64, 380, 1),
                AbstractObjectFactory.generateOfType(ObjectType.HIDDEN_SPIKE,900, 600),
                cloudMaker.generate(100, 50, 3, 1.5),
                cloudMaker.generate(500, 100, 3, 1.5),
                cloudMaker.generate(900, 90, 3, 1.5),
                cloudMaker.generate(1400, 70, 3, 1.5));

        return s;
    }

    public static Scene loadEasyLevel() {
        Scene s = createScene();
        return s;
    }
}
