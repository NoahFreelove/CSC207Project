package com.project.game.UIFactories;

import com.project.engine.Core.Engine;
import com.project.engine.Core.Window.GameWindow;

public class EngineFactory {

    private static final Engine e = Engine.getInstance();
    private static final GameWindow w = e.getPrimaryWindow();

    public static Engine createEngine() {

        if (w == null) {
            System.out.println("Failed to obtain primary window");
            e.exitEngine();
            return null;
        }

        while (!w.isReady()) {}

        return e;
    }

    public static GameWindow getWindow() {
        return w;
    }

}
