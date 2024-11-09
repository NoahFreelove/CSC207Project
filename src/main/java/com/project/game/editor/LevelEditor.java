package com.project.game.editor;

import com.project.engine.Core.Engine;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Tuple;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.EngineMain;
import com.project.engine.UI.*;

import javax.swing.*;
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
        background.setLayout(null); // Use null layout for manual positioning
        background.setBackground(Color.decode("#595959"));

        // Left sidebar
        GameUIPanel leftSidebar = new GameUIPanel(0, 0, 400, windowSize.getSecond());
        leftSidebar.setBackground(Color.decode("#404040"));
        leftSidebar.setLayout(new GridLayout(12, 1)); // Set GridLayout with one column
        background.add(leftSidebar);

        // Right sidebar
        GameUIPanel rightSidebar = new GameUIPanel(windowSize.getFirst() - 400, 0, 400, windowSize.getSecond());
        rightSidebar.setBackground(Color.decode("#404040"));
        background.add(rightSidebar);

        // Center area
        GameUIPanel centerPanel = new GameUIPanel(400, 0, windowSize.getFirst() - 800, windowSize.getSecond());
        centerPanel.setBackground(Color.decode("#FFFFFF"));
        background.add(centerPanel);

        GameUILabel title = new GameUILabel("Level Editor", 40, 40, 300, 50);
        title.setForeground(Color.WHITE);
        title.setFontSize(50);
        title.setOpaque(false);
        // center
        title.setHorizontalAlignment(SwingConstants.CENTER);

        GameUIButton saveButton = new GameUIButton("Save", 40, 100, 300, 50);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFontSize(32);
        saveButton.setBackground(Color.decode("#398a32"));
        // add border to button
        saveButton.setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 5));


        GameUIButton loadButton = new GameUIButton("Load", 40, 160, 300, 50);
        loadButton.setForeground(Color.WHITE);
        loadButton.setFontSize(32);
        loadButton.setBackground(Color.decode("#32748a"));
        loadButton.setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 5));

        GameUIButton testButton = new GameUIButton("Test", 40, 220, 300, 50);
        testButton.setForeground(Color.WHITE);
        testButton.setFontSize(32);
        testButton.setBackground(Color.decode("#324f8a"));
        testButton.setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 5));

        GameUIButton exportButton = new GameUIButton("Export", 40, 280, 300, 50);
        exportButton.setForeground(Color.WHITE);
        exportButton.setFontSize(32);
        exportButton.setBackground(Color.decode("#66328a"));
        exportButton.setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 5));

        GameUIButton backToGameButton = new GameUIButton("Back to Game", 40, 340, 300, 50);
        backToGameButton.setForeground(Color.WHITE);
        backToGameButton.setFontSize(32);
        backToGameButton.setBackground(Color.decode("#8a3a32"));
        backToGameButton.setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 5));

        backToGameButton.onClickEvent = LevelEditor::exitLevelEditor;

        // GameUICheckbox box = new GameUICheckbox("Text");
        // box.setForeground(Color.WHITE);
        // box.setFontSize(32);

        // Add components to left sidebar
        leftSidebar.add(title);
        // add spacer
        leftSidebar.add(new GameUIPanel().setBackground("#404040"));

        leftSidebar.add(saveButton);

        leftSidebar.add(loadButton);
        leftSidebar.add(testButton);
        leftSidebar.add(exportButton);

        // add spacer
        leftSidebar.add(new GameUIPanel().setBackground("#404040"));

        leftSidebar.add(backToGameButton);

        scene.addUIElement(background);

        return scene;
    }

    public static void loadFromFile(String path) {

    }

    public static void saveToFile(String path) {

    }

    public static void exportToFile(String path) {

    }

    private static void exitLevelEditor() {
        Engine.getInstance().getPrimaryWindow().setWindowSizeForce(800, 800);
        EngineMain.loadTestScene();
    }
}