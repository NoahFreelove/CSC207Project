package com.project.use_cases.level_editing;

import com.project.entity.ui.GameUI;
import com.project.use_cases.general.GameInteractor;
import com.project.entity.core.Scene;
import com.project.entity.core.Tuple;
import com.project.use_cases.general.GameOutputData;
import com.project.external_interfaces.ImageLoader;
import com.project.view.ui.GameLabelView;
import com.project.entity.ui.GameUIPanel;
import com.project.use_cases.general.LoadingScreen;
import com.project.use_cases.play_prebuilt_levels.scenes.LevelSelectionFactory;
import com.project.use_cases.ui.button.ButtonOutputData;

import javax.swing.*;
import java.awt.*;

import static com.project.use_cases.level_editing.LevelEditor.levelEditorScreenSize;

public class LevelEditorFactory {
    public static Scene generateLevelEditor() {
        LevelEditor scene = new LevelEditor();
        GameOutputData w = GameInteractor.getInstance().getPrimaryWindow();
        if (w == null) {
            System.err.println("No window found");
            return Scene.NullScene();
        }

        Tuple<Integer, Integer> windowSize = w.getWindowSize();
        GameUIPanel background = new GameUIPanel(0, 0, windowSize.getFirst(), windowSize.getSecond());
        background.setLayout(null); // Use null layout for manual positioning
        background.setOpaque(false);

        // Left sidebar
        GameUIPanel leftSidebar = new GameUIPanel(0, 0, 200, windowSize.getSecond());
        leftSidebar.setBackground(Color.decode("#404040"));
        leftSidebar.setLayout(new GridLayout(12, 1)); // Set GridLayout with one column
        background.add(leftSidebar);

        // Right sidebar
        GameUIPanel rightSidebar = new GameUIPanel(windowSize.getFirst() - 400, 0, 400, windowSize.getSecond());
        rightSidebar.setBackground(Color.decode("#404040"));
        rightSidebar.setLayout(new GridLayout(12, 1)); // Set GridLayout with one column
        background.add(rightSidebar);

        // Center area
        GameUIPanel centerPanel = new GameUIPanel(500, 0, windowSize.getFirst() - 700, windowSize.getSecond());
        centerPanel.setOpaque(false);
        background.add(centerPanel);

        GameLabelView title = new GameLabelView("Editor", 40, 40, 300, 50);
        title.setForeground(Color.WHITE);
        title.setFontSize(40);
        title.setOpaque(false);
        // center
        title.setHorizontalAlignment(SwingConstants.CENTER);

        ButtonOutputData saveButton = new ButtonOutputData("Save", 40, 100, 300, 50);
        saveButton.getComponent().setForeground(Color.WHITE);
        saveButton.setFontSize(32);
        saveButton.getComponent().setBackground(Color.decode("#398a32"));
        // add border to button
        saveButton.getComponent().setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 5));
        saveButton.setButtonCallback(() -> {
            // scene.activeFile == "", we need to ask user for folder to store with a file picker
            // then in a message box ask for file name, append .json to the end of it and set scene.activeFile to that
            // now that scene.activeFile != "", call scene.saveToFile()
            if (scene.activeFile.isEmpty()) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");
                int userSelection = fileChooser.showSaveDialog(w.getRootPane());
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    scene.activeFile = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!scene.activeFile.endsWith(".json")) {
                        scene.activeFile += ".json";
                    }
                    scene.saveToFile();
                }
            } else {
                scene.saveToFile();
            }
            GameInteractor.getInstance().getPrimaryWindow().refocusInWindow();

        });


        ButtonOutputData loadButton = new ButtonOutputData("Load", 40, 160, 300, 50);
        loadButton.getComponent().setForeground(Color.WHITE);
        loadButton.setFontSize(32);
        loadButton.getComponent().setBackground(Color.decode("#32748a"));
        loadButton.getComponent().setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 5));

        loadButton.setButtonCallback(() -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to load");
            int userSelection = fileChooser.showOpenDialog(w.getRootPane());
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                scene.loadFromFile(fileChooser.getSelectedFile().getAbsolutePath(), false);
            }
            GameInteractor.getInstance().getPrimaryWindow().refocusInWindow();
        });

        ButtonOutputData testButton = new ButtonOutputData("Test", 40, 220, 300, 50);
        testButton.getComponent().setForeground(Color.WHITE);
        testButton.setFontSize(32);
        testButton.getComponent().setBackground(Color.decode("#324f8a"));
        testButton.getComponent().setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 5));
        testButton.setButtonCallback(() -> {
            w.setWindowSizeForce(800, 800);
            LoadingScreen.addLoadingScreen(scene.exportToScene(true));
            w.setActiveScene(scene.exportToScene(true));
            LevelSelectionFactory.isInEditor = true;
            GameInteractor.getInstance().getPrimaryWindow().refocusInWindow();
        });

        ButtonOutputData newButton = new ButtonOutputData("New", 40, 280, 300, 50);
        newButton.getComponent().setForeground(Color.WHITE);
        newButton.setFontSize(32);
        newButton.getComponent().setBackground(Color.decode("#66328a"));
        newButton.getComponent().setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 5));
        newButton.setButtonCallback(() -> {
            scene.newFile();
            GameInteractor.getInstance().getPrimaryWindow().refocusInWindow();
        });

        ButtonOutputData backToGameButton = new ButtonOutputData("Back to Game", 40, 340, 300, 50);
        backToGameButton.getComponent().setForeground(Color.WHITE);
        backToGameButton.setFontSize(20);
        backToGameButton.getComponent().setBackground(Color.decode("#8a3a32"));
        backToGameButton.getComponent().setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 5));

        backToGameButton.setButtonCallback(scene::exitLevelEditor);

        // GameUICheckbox box = new GameUICheckbox("Text");
        // box.setForeground(Color.WHITE);
        // box.setFontSize(32);

        // Add components to left sidebar
        leftSidebar.add(title);
        // add spacer
        leftSidebar.add(new GameUIPanel().setBackground("#404040"));

        leftSidebar.add(saveButton.getComponent());

        leftSidebar.add(loadButton.getComponent());
        leftSidebar.add(testButton.getComponent());

        // add spacer
        leftSidebar.add(new GameUIPanel().setBackground("#404040"));
        leftSidebar.add(newButton.getComponent());

        leftSidebar.add(backToGameButton.getComponent());

        GameLabelView selectedTileLabel = new GameLabelView("Selected Tile");
        selectedTileLabel.setForeground(Color.WHITE);
        selectedTileLabel.setFontSize(32);
        // center
        selectedTileLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel selectedTileIcon = new JLabel();
        selectedTileIcon.setIcon(new ImageIcon(ImageLoader.loadImage(EditorTileCache.spriteCache.get(1), EditorTileCache.BASE_TILE_SIZE, EditorTileCache.BASE_TILE_SIZE)));
        selectedTileIcon.setHorizontalAlignment(SwingConstants.CENTER);

        // add integer ui slider
        JSlider tileSlider = new JSlider(JSlider.HORIZONTAL, 1, EditorTileCache.spriteCache.size(), 2);
        tileSlider.addChangeListener(e -> {
            int value = tileSlider.getValue() - 1;
            selectedTileIcon.setIcon(new ImageIcon(ImageLoader.loadImage(EditorTileCache.spriteCache.get(value), EditorTileCache.BASE_TILE_SIZE, EditorTileCache.BASE_TILE_SIZE)));
            scene.selectedTileType = value;
            GameInteractor.getInstance().getPrimaryWindow().refocusInWindow();
        });

        JButton chooseTileButton = new JButton("Choose Tile");
        chooseTileButton.addActionListener(e -> {
            scene.selectedTileType = tileSlider.getValue()-1;
            GameInteractor.getInstance().getPrimaryWindow().refocusInWindow();
        });

        tileSlider.setMajorTickSpacing(1);
        tileSlider.setPaintTicks(true);
        tileSlider.setSnapToTicks(true);
        tileSlider.setBackground(Color.decode("#404040"));
        tileSlider.setForeground(Color.WHITE);
        tileSlider.setFont(new Font("Arial", Font.PLAIN, 20));

        // Add scale x, scale y, rotation sliders with labels, cap 0.5 - 4 in increments of 0.5
        // rotation should be 0-270 in increments of 90
        // both of these should be in the right sidebar under the selected tile

        JSlider scaleXSlider = new JSlider(JSlider.HORIZONTAL, 1, 4, 1);
        scaleXSlider.addChangeListener(e -> {
            int value = scaleXSlider.getValue();
            scene.modifyTile(value, -1, -1);
            w.refocusInWindow();
        });
        scaleXSlider.setMajorTickSpacing(1);
        scaleXSlider.setPaintTicks(true);
        scaleXSlider.setPaintLabels(true);
        scaleXSlider.setSnapToTicks(true);
        scaleXSlider.setBackground(Color.decode("#404040"));
        scaleXSlider.setForeground(Color.WHITE);


        JSlider scaleYSlider = new JSlider(JSlider.HORIZONTAL, 1, 4, 1);
        scaleYSlider.addChangeListener(e -> {
            int value = scaleYSlider.getValue();
            scene.modifyTile(-1, value, -1);
            w.refocusInWindow();
        });

        scaleYSlider.setMajorTickSpacing(1);
        scaleYSlider.setPaintTicks(true);
        scaleYSlider.setPaintLabels(true);
        scaleYSlider.setSnapToTicks(true);
        scaleYSlider.setBackground(Color.decode("#404040"));
        scaleYSlider.setForeground(Color.WHITE);

        JSlider rotationSlider = new JSlider(JSlider.HORIZONTAL, 0, 270, 0);
        rotationSlider.addChangeListener(e -> {
            int value = rotationSlider.getValue();
            scene.modifyTile(-1, -1, value);
            w.refocusInWindow();
        });
        rotationSlider.setMajorTickSpacing(90);
        rotationSlider.setPaintTicks(true);
        rotationSlider.setPaintLabels(true);
        rotationSlider.setSnapToTicks(true);
        rotationSlider.setBackground(Color.decode("#404040"));
        rotationSlider.setForeground(Color.WHITE);

        // labels
        GameLabelView scaleXLabel = new GameLabelView("Scale X");
        scaleXLabel.setForeground(Color.WHITE);
        scaleXLabel.setFontSize(20);
        scaleXLabel.setHorizontalAlignment(SwingConstants.CENTER);

        GameLabelView scaleYLabel = new GameLabelView("Scale Y");
        scaleYLabel.setForeground(Color.WHITE);
        scaleYLabel.setFontSize(20);
        scaleYLabel.setHorizontalAlignment(SwingConstants.CENTER);

        GameLabelView rotationLabel = new GameLabelView("Rotation");
        rotationLabel.setForeground(Color.WHITE);
        rotationLabel.setFontSize(20);
        rotationLabel.setHorizontalAlignment(SwingConstants.CENTER);


        // Add components to right sidebar
        rightSidebar.add(selectedTileLabel);
        rightSidebar.add(selectedTileIcon);
        rightSidebar.add(tileSlider);
        //rightSidebar.add(chooseTileButton);

        rightSidebar.add(scaleXLabel);
        rightSidebar.add(scaleXSlider);
        rightSidebar.add(scaleYLabel);
        rightSidebar.add(scaleYSlider);
        rightSidebar.add(rotationLabel);
        rightSidebar.add(rotationSlider);

        scene.addUIElement(new GameUI(background));

        return scene;
    }

    public static void loadLevelEditor(GameOutputData w) {
        w.setWindowSizeForce(levelEditorScreenSize.getFirst(),levelEditorScreenSize.getSecond());

        Scene s = LevelEditorFactory.generateLevelEditor();
        w.setActiveScene(s);
    }
}
