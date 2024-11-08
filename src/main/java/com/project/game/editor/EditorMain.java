package com.project.game.editor;

import com.project.engine.Core.Engine;
import com.project.engine.Core.Window.GameWindow;

public class EditorMain {

    public static void main(String[] args) {
        GameWindow w = Engine.getInstance().getPrimaryWindow();
        if (w == null)
            return;
        while (!w.isReady()){}
        w.setWindowSizeForce(1920,1080);
        w.setActiveScene(LevelEditor.generateLevelEditor());

    }
}
