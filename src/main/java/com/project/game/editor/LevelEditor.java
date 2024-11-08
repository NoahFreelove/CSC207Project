package com.project.game.editor;

import com.project.engine.Core.Engine;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Tuple;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.UI.GameUIPanel;

import java.awt.*;

public class LevelEditor {

    public static Scene generateLevelEditor() {
        Scene scene = new Scene();
        GameWindow w = Engine.getInstance().getPrimaryWindow();
        if (w == null) {
            System.err.println("No window found");
            return Scene.NullScene();
        }

        Tuple<Integer, Integer> windowSize = w.getWindowSize();
        GameUIPanel background = new GameUIPanel(0, 0, windowSize.getFirst(), windowSize.getSecond());
        background.setBackground(Color.RED);
        scene.addUIElement(background);

        return scene;
    }

    public static void loadFromFile(String path) {

    }


    /**
     * Exports to format readable by level editor
     * @param path
     */
    public static void saveToFile(String path) {

    }

    /**
     * Exports to format readable by game loader
     * @param path
     */
    public static void exportToFile(String path) {

    }
}
