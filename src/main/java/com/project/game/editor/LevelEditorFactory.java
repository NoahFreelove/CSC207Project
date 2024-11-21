package com.project.game.editor;

import com.project.engine.Core.Engine;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Tuple;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.IO.ImageLoader;
import com.project.engine.UI.GameUIButton;
import com.project.engine.UI.GameUILabel;
import com.project.engine.UI.GameUIPanel;
import com.project.game.Scenes.MainMenuFactory;
import com.project.game.TileCache;

import javax.swing.*;
import java.awt.*;

public class LevelEditorFactory {
    public static Scene generateLevelEditor() {
        LevelEditor scene = new LevelEditor();
        GameWindow w = Engine.getInstance().getPrimaryWindow();
        if (w == null) {
            System.err.println("No window found");
            return Scene.NullScene();
        }

        Tuple<Integer, Integer> windowSize = w.getWindowSize();
        GameUIPanel background = new GameUIPanel(0, 0, windowSize.getFirst(), windowSize.getSecond());
        background.setLayout(null); // Use null layout for manual positioning
        background.setOpaque(false);

        // Left sidebar
        GameUIPanel leftSidebar = new GameUIPanel(0, 0, 400, windowSize.getSecond());
        leftSidebar.setBackground(Color.decode("#404040"));
        leftSidebar.setLayout(new GridLayout(12, 1)); // Set GridLayout with one column
        background.add(leftSidebar);

        // Right sidebar
        GameUIPanel rightSidebar = new GameUIPanel(windowSize.getFirst() - 300, 0, 300, windowSize.getSecond());
        rightSidebar.setBackground(Color.decode("#404040"));
        rightSidebar.setLayout(new GridLayout(12, 1)); // Set GridLayout with one column
        background.add(rightSidebar);

        // Center area
        GameUIPanel centerPanel = new GameUIPanel(500, 0, windowSize.getFirst() - 700, windowSize.getSecond());
        centerPanel.setOpaque(false);
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

        backToGameButton.onClickEvent = scene::exitLevelEditor;

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

        GameUILabel selectedTileLabel = new GameUILabel("Selected Tile");
        selectedTileLabel.setForeground(Color.WHITE);
        selectedTileLabel.setFontSize(32);
        // center
        selectedTileLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel selectedTileIcon = new JLabel();
        selectedTileIcon.setIcon(new ImageIcon(ImageLoader.loadImage(TileCache.spriteCache.get(0), TileCache.BASE_TILE_SIZE, TileCache.BASE_TILE_SIZE)));
        selectedTileIcon.setHorizontalAlignment(SwingConstants.CENTER);

        // add integer ui slider
        JSlider tileSlider = new JSlider(JSlider.HORIZONTAL, 0, TileCache.spriteCache.size() - 1, 0);
        tileSlider.addChangeListener(e -> {
            int value = tileSlider.getValue();
            selectedTileIcon.setIcon(new ImageIcon(ImageLoader.loadImage(TileCache.spriteCache.get(value), TileCache.BASE_TILE_SIZE, TileCache.BASE_TILE_SIZE)));
        });

        JButton chooseTileButton = new JButton("Choose Tile");
        chooseTileButton.addActionListener(e -> {
            scene.selectedTileType = tileSlider.getValue();
            Engine.getInstance().getPrimaryWindow().refocusInWindow();
        });
        tileSlider.setMajorTickSpacing(1);
        tileSlider.setPaintTicks(true);
        tileSlider.setPaintLabels(true);
        tileSlider.setSnapToTicks(true);
        tileSlider.setBackground(Color.decode("#404040"));
        tileSlider.setForeground(Color.WHITE);
        tileSlider.setFont(new Font("Arial", Font.PLAIN, 20));

        // Add components to right sidebar
        rightSidebar.add(selectedTileLabel);
        rightSidebar.add(selectedTileIcon);
        rightSidebar.add(tileSlider);
        rightSidebar.add(chooseTileButton);


        scene.addUIElement(background);

        return scene;
    }

    public static void loadLevelEditor(GameWindow w) {
        w.setWindowSizeForce(1920, 1080);
        Scene s = LevelEditorFactory.generateLevelEditor();
        w.setActiveScene(s);
    }
}
