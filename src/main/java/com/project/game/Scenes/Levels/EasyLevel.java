package com.project.game.Scenes.Levels;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Tuple;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.UI.GameUIButton;
import com.project.game.ObjectFactories.*;
import com.project.game.Scenes.ISceneLoader;
import com.project.game.Scenes.LevelSelectionFactory;
import com.project.game.Scenes.PauseOverlayFactory;
import com.project.game.Scenes.WinOverlayFactory;
import com.project.game.Scripts.SceneExit;
import com.project.game.UIFactories.UIFactory;

/**
 * A simple Level for Game PoC
 */
public class EasyLevel implements ISceneLoader {
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

        s.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.BACKGROUND, 0, -128));

        GameObject player = AbstractObjectFactory.generateOfType(ObjectType.PLAYER, 300, 400, 10);
        s.getCamera().update(player, 0);
        s.getCamera().setOffsetX(-100);
        s.getCamera().setOffsetY(100);

        s.getCamera().setFollowY(false);

        s.addSceneObject(player, true);
        s.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.VOID));
        s.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.BOUNDARY, 0, 0,
                5, Engine.getInstance().getPrimaryWindow().getActualWindowSize().getSecond()));

        GroundBlockFactory groundMaker = (GroundBlockFactory) AbstractObjectFactory.makeFactory(ObjectType.GROUND_BLOCK);
        HiddenBlockFactory hiddenBlockMaker = (HiddenBlockFactory) AbstractObjectFactory.makeFactory(ObjectType.HIDDEN_BLOCK);
        CloudFactory cloudMaker = (CloudFactory) AbstractObjectFactory.makeFactory(ObjectType.CLOUD);

        PrincessFactory princess = (PrincessFactory) AbstractObjectFactory.makeFactory(ObjectType.PRINCESS);

        s.addSceneObjects(
                groundMaker.generate(0, 600, 2, 10, 2, 0.8),
                groundMaker.generate(760, 500, 2, 1, 1, 0.8),
                groundMaker.generate(1600, 600, 2, 10, 2, 0.8),
                groundMaker.generate(500, 520, 1, 1, 1, "assets/brick.png"),
                groundMaker.generate(700, 400, 1, 1, 0.5, 1, "assets/brick.png"),
                groundMaker.generate(0, 600, 2, 20, 4, 0.8),
                groundMaker.generate(1600, 600, 2, 20, 4, 0.8),
                groundMaker.generate(500, 600-64, 2, 2, 1, "assets/brick.png"),
                groundMaker.generate(764, 400-64, 2, 1, 1, 1, "assets/brick.png"),
                groundMaker.generate(700, 400, 2, 1, 1, 1, "assets/brick.png"),
                hiddenBlockMaker.generate(1280+64, 380, 1, 1,1, 0.8),
                hiddenBlockMaker.generate(1280, 380, 1, 1,1, 0.8),
                hiddenBlockMaker.generate(1280-64, 380, 1, 1, 1,0.8),
                AbstractObjectFactory.generateOfType(ObjectType.HIDDEN_SPIKE,900, 600),
                //AbstractObjectFactory.generateOfType(ObjectType.HIDDEN_SPIKE,730, 400, 0),
                cloudMaker.generate(100, 50, 3, 1.5),
                cloudMaker.generate(500, 100, 3, 1.5),
                cloudMaker.generate(900, 90, 3, 1.5),
                cloudMaker.generate(1400, 70, 3, 1.5),
                AbstractObjectFactory.generateOfType(ObjectType.ENEMY, 500, 300, 2,1, 1),
                princess.generate(2000, 516, 1, 1));


        // Testing
        GameUIButton pause = UIFactory.ButtonFactory("Pause", 600, 10, 220, 50);

        pause.onClickEvent = PauseOverlayFactory::pauseGame;
        s.addUIElement(pause);
        s.setScaleX(1.25f);
        s.setScaleY(1.25f);
        return s;
    }

    public Scene loadScene() {
        Scene s = createScene();
        Engine.getInstance().getPrimaryWindow().setActiveScene(s);
        Engine.getInstance().unpauseGame();
        WinOverlayFactory.removeWinOverlay();
        return s;
    }
}
