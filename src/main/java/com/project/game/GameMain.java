package com.project.game;

import com.project.engine.Core.Engine;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Window.GameWindow;
import com.project.game.Scenes.MainMenuFactory;
import com.project.game.tts.PlayTTS;

public class GameMain {
    public static void main(String[] args) {
        Engine e = Engine.getInstance();
        e.closeHook = PlayTTS::shutdown;
        GameWindow w = e.getPrimaryWindow();
        if (w == null) {
            System.out.println("Failed to obtain primary window");
            System.exit(1);
            return;
        }

        while (!w.isReady()) {}
        //String serialized = FileIO.ReadText("tmp/serialized_scene.json");
        //w.setActiveScene(SerializeManager.deserialize(serialized));
        //EasyLevelFactory.loadEasyLevel();
        Scene main = MainMenuFactory.createScene();
        w.setActiveScene(main);
    }
}