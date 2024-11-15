package com.project.game.Scenes.Levels;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Tuple;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.UI.GameUIButton;
import com.project.game.ObjectFactories.*;
import com.project.game.Scenes.LevelSelectionFactory;
import com.project.game.Scenes.PauseOverlayFactory;
import com.project.game.Scripts.SceneExit;
import com.project.game.UIFactories.UIFactory;

/**
 * A simple Level for Game PoC
 */
public class EasyLevel {
    public static Scene createScene() {
        Tuple<Engine, GameWindow> out = Engine.createAndWait();
        Engine e = out.getFirst();
        GameWindow w = out.getSecond();
        //String serialized = FileIO.ReadText("tmp/serialized_scene.json");
        //w.setActiveScene(SerializeManager.deserialize(serialized));
        Scene s = new Scene("Test Scene");

        GameObject exitInputLIstener = AbstractObjectFactory.generateOfType(ObjectType.ESCAPE_KEY);
        exitInputLIstener.getScriptable(SceneExit.class).setOnExit(LevelSelectionFactory::loadLevelSelection);
        s.addSceneObject(exitInputLIstener, true);

        s.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.BACKGROUND));

        GameObject player = AbstractObjectFactory.generateOfType(ObjectType.PLAYER, 10);
        s.getCamera().update(player, 0);
        s.getCamera().setOffsetX(-100);
        s.getCamera().setFollowY(false);

        s.addSceneObject(player, true);
        s.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.VOID));
        s.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.BOUNDARY, 0, 0,
                5, Engine.getInstance().getPrimaryWindow().getActualWindowSize().getSecond()));

        GroundBlockFactory groundMaker = (GroundBlockFactory) AbstractObjectFactory.makeFactory(ObjectType.GROUND_BLOCK);
        HiddenBlockFactory hiddenBlockMaker = (HiddenBlockFactory) AbstractObjectFactory.makeFactory(ObjectType.HIDDEN_BLOCK);
        CloudFactory cloudMaker = (CloudFactory) AbstractObjectFactory.makeFactory(ObjectType.CLOUD);

        s.addSceneObjects(
                groundMaker.generate(0, 600, 2, 10, 2, 0.8),
                groundMaker.generate(1600, 600, 2, 10, 2, 0.8),
                groundMaker.generate(500, 520, 1, 1, 1, "assets/brick.png"),
                groundMaker.generate(700, 400, 1, 1, 0.5, 1, "assets/brick.png"),
                hiddenBlockMaker.generate(1280, 380, 1, 1,1, 0.8),
                hiddenBlockMaker.generate(1280-64, 380, 1, 1, 1,0.8),
                AbstractObjectFactory.generateOfType(ObjectType.HIDDEN_SPIKE,900, 600),
                AbstractObjectFactory.generateOfType(ObjectType.HIDDEN_SPIKE,730, 400, 0),
                cloudMaker.generate(100, 50, 3, 1.5),
                cloudMaker.generate(500, 100, 3, 1.5),
                cloudMaker.generate(900, 90, 3, 1.5),
                cloudMaker.generate(1400, 70, 3, 1.5));
        // Testing
        GameUIButton test = UIFactory.ButtonFactory("Pause", 600, 10, 220, 50);

        test.onClickEvent = PauseOverlayFactory::pauseGame;
        s.addUIElement(test);

        return s;
    }

    public static Scene loadEasyLevel() {
        Scene s = createScene();
        Engine.getInstance().getPrimaryWindow().setActiveScene(s);
        return s;
    }
}
