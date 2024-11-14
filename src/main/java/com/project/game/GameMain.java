package com.project.game;

import com.project.engine.Core.Engine;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Tuple;
import com.project.engine.Core.Window.GameWindow;
import com.project.game.Scenes.MainMenuFactory;
import com.project.game.tts.PlayTTS;

public class GameMain {
    public static void main(String[] args) {
        Tuple<Engine, GameWindow> out = Engine.createAndWait();
        Engine e = out.getFirst();
        GameWindow w = out.getSecond();
        e.closeHook = PlayTTS::shutdown;

        Scene main = MainMenuFactory.createScene();
        w.setActiveScene(main);
    }
}