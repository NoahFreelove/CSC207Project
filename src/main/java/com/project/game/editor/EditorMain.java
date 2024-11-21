package com.project.game.editor;

import com.project.engine.Core.Engine;
import com.project.engine.Core.Window.GameWindow;

public class EditorMain {
    public static void main(String[] args) {
        System.out.println(args.getClass().getTypeName());
        GameWindow w = Engine.getInstance().getPrimaryWindow();
        if (w == null)
            return;
        while (!w.isReady()){}
        LevelEditorFactory.loadLevelEditor(w);
    }
}
