package com.project.game;

import com.project.engine.Core.Engine;
import com.project.engine.Core.Scene;
import com.project.game.UIFactories.EngineFactory;
import com.project.game.Scenes.MainMenuFactory;
import com.project.game.tts.PlayTTS;

public class GameMain {
    public static void main(String[] args) {
        Engine e = EngineFactory.createEngine();
        e.closeHook = PlayTTS::shutdown;


        //while (!w.isReady()) {}
        //String serialized = FileIO.ReadText("tmp/serialized_scene.json");
        //w.setActiveScene(SerializeManager.deserialize(serialized));
        //EasyLevelFactory.loadEasyLevel();
        Scene main = MainMenuFactory.createScene();
        EngineFactory.getWindow().setActiveScene(main);
    }
}