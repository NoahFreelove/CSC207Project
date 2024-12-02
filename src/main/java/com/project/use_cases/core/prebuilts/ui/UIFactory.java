package com.project.use_cases.core.prebuilts.ui;


import com.project.entity.core.GameObject;
import com.project.entity.core.Scene;
import com.project.entity.core.Tuple;
import com.project.entity.ui.*;
import com.project.use_cases.core.editor.LevelEditor;
import com.project.use_cases.core.game.GameInteractor;
import com.project.use_cases.core.game.GameOutputData;
import com.project.use_cases.core.prebuilts.scenes.LevelSelectionFactory;
import com.project.use_cases.core.prebuilts.ui.types.button.ButtonOutputData;
import com.project.use_cases.core.prebuilts.ui.types.label.LabelOutputData;
import com.project.use_cases.core.prebuilts.ui.types.panel.PanelOutputData;
import com.project.use_cases.core.prebuilts.ui.types.slider.SliderOutputData;
import com.project.use_cases.editor.*;
import com.project.use_cases.game_pause.GameUnpauseInteractor;
import com.project.use_cases.game_reset.LevelResetInteractor;
import com.project.use_cases.game_reset.LoadLevelSelectInteractor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static com.project.use_cases.core.prebuilts.ui.UIConstants.*;

public class UIFactory {
    public static ArrayList<Object> PausedElements = new ArrayList<>();
    public static LabelOutputData deathCounter;

    public static GameUI generateEditorUI(GameOutputData w, LevelEditor scene) {
        Tuple<Integer, Integer> windowSize = w.getWindowSize();
        PanelOutputData background = new PanelOutputData(0, 0, windowSize.getFirst(), windowSize.getSecond());
        background.getComponent().setLayout(null); // Use null layout for manual positioning
        background.getComponent().setOpaque(false);
        PanelOutputData leftSidebar = new PanelOutputData(0, 0, 200, windowSize.getSecond());
        leftSidebar.getComponent().setBackground(Color.decode("#404040"));
        leftSidebar.getComponent().setLayout(new GridLayout(12, 1)); // Set GridLayout with one column
        background.add(leftSidebar);

        // Right sidebar
        PanelOutputData rightSidebar = new PanelOutputData(windowSize.getFirst() - 400, 0, 400, windowSize.getSecond());
        rightSidebar.getComponent().setBackground(Color.decode("#404040"));
        rightSidebar.getComponent().setLayout(new GridLayout(12, 1)); // Set GridLayout with one column
        background.add(rightSidebar);

        // Center area
        PanelOutputData centerPanel = new PanelOutputData(500, 0, windowSize.getFirst() - 700, windowSize.getSecond());
        centerPanel.getComponent().setOpaque(false);
        background.add(centerPanel);

        LabelOutputData title = new LabelOutputData("Editor", 40, 40, 300, 50);
        title.getComponent().setForeground(Color.WHITE);
        title.setFontSize(40);
        title.getComponent().setOpaque(false);

        ButtonOutputData saveButton = new ButtonOutputData("Save", 40, 100, 300, 50);
        saveButton.getComponent().setForeground(Color.WHITE);
        saveButton.setFontSize(32);
        saveButton.getComponent().setBackground(Color.decode("#398a32"));
        // add border to button
        saveButton.getComponent().setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 5));
        saveButton.setButtonCallback(() -> { EditorSaveInteractor.execute(w, scene); });


        ButtonOutputData loadButton = new ButtonOutputData("Load", 40, 160, 300, 50);
        loadButton.getComponent().setForeground(Color.WHITE);
        loadButton.setFontSize(32);
        loadButton.getComponent().setBackground(Color.decode("#32748a"));
        loadButton.getComponent().setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 5));

        loadButton.setButtonCallback(() -> { EditorLoadInteractor.execute(w, scene); });

        ButtonOutputData testButton = new ButtonOutputData("Test", 40, 220, 300, 50);
        testButton.getComponent().setForeground(Color.WHITE);
        testButton.setFontSize(32);
        testButton.getComponent().setBackground(Color.decode("#324f8a"));
        testButton.getComponent().setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 5));
        testButton.setButtonCallback(() -> { EditorTestInteractor.execute(w, scene); });

        ButtonOutputData newButton = new ButtonOutputData("New", 40, 280, 300, 50);
        newButton.getComponent().setForeground(Color.WHITE);
        newButton.setFontSize(32);
        newButton.getComponent().setBackground(Color.decode("#66328a"));
        newButton.getComponent().setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 5));
        newButton.setButtonCallback(() -> {EditorNewInteractor.execute(scene); });

        ButtonOutputData backToGameButton = new ButtonOutputData("Back to Game", 40, 340, 300, 50);
        backToGameButton.getComponent().setForeground(Color.WHITE);
        backToGameButton.setFontSize(20);
        backToGameButton.getComponent().setBackground(Color.decode("#8a3a32"));
        backToGameButton.getComponent().setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 5));

        backToGameButton.setButtonCallback(EditorExitInteractor::execute);

        // Add components to left sidebar
        leftSidebar.add(title);
        // add spacer
        leftSidebar.add(new PanelOutputData().setBackground("#404040"));

        leftSidebar.add(saveButton);

        leftSidebar.add(loadButton);
        leftSidebar.add(testButton);

        // add spacer
        leftSidebar.add(new PanelOutputData().setBackground("#404040"));
        leftSidebar.add(newButton);

        leftSidebar.add(backToGameButton);

        LabelOutputData selectedTileLabel = new LabelOutputData("Selected Tile");
        selectedTileLabel.getComponent().setForeground(Color.WHITE);
        selectedTileLabel.setFontSize(32);

        LabelOutputData selectedTileIcon = new LabelOutputData();
        selectedTileIcon.setEditorIcon(1);

        // add integer ui slider
        SliderOutputData tileSlider = new SliderOutputData(1, 0, 2);
        tileSlider.setSliderCallback(() -> {
            int value = tileSlider.getValue() - 1;
            selectedTileIcon.setEditorIcon(value);
            scene.selectedTileType = value;
            GameInteractor.getInstance().getPrimaryWindow().refocusInWindow();
        });

        // Add scale x, scale y, rotation sliders with labels, cap 0.5 - 4 in increments of 0.5
        // rotation should be 0-270 in increments of 90
        // both of these should be in the right sidebar under the selected tile

        SliderOutputData scaleXSlider = new SliderOutputData(1, 4, 1);
        scaleXSlider.setSliderCallback(() -> {
            int value = scaleXSlider.getValue();
            scene.modifyTile(value, -1, -1);
            w.refocusInWindow();
        });


        SliderOutputData scaleYSlider = new SliderOutputData(1, 4, 1);
        scaleYSlider.setSliderCallback(() -> {
            int value = scaleYSlider.getValue();
            scene.modifyTile(-1, value, -1);
            w.refocusInWindow();
        });

        SliderOutputData rotationSlider = new SliderOutputData(0, 270, 0);
        rotationSlider.setSliderCallback(() -> {
            int value = rotationSlider.getValue();
            scene.modifyTile(-1, -1, value);
            w.refocusInWindow();
        });
        rotationSlider.setSpacing(90);

        // labels
        LabelOutputData scaleXLabel = new LabelOutputData("Scale X");
        scaleXLabel.getComponent().setForeground(Color.WHITE);
        scaleXLabel.setFontSize(20);

        LabelOutputData scaleYLabel = new LabelOutputData("Scale Y");
        scaleYLabel.getComponent().setForeground(Color.WHITE);
        scaleYLabel.setFontSize(20);

        LabelOutputData rotationLabel = new LabelOutputData("Rotation");
        rotationLabel.getComponent().setForeground(Color.WHITE);
        rotationLabel.setFontSize(20);


        // Add components to right sidebar
        rightSidebar.add(selectedTileLabel);
        rightSidebar.add(selectedTileIcon);
        rightSidebar.add(tileSlider);

        rightSidebar.add(scaleXLabel);
        rightSidebar.add(scaleXSlider);
        rightSidebar.add(scaleYLabel);
        rightSidebar.add(scaleYSlider);
        rightSidebar.add(rotationLabel);
        rightSidebar.add(rotationSlider);

        return new GameUI(background);
    }

    public static void createWinOverlay(Scene scene) {
        // Label
        LabelOutputData label = UIFactory.LabelFactory("Level Completed", 50, 40, 700, 200, FOREST_GREEN);

        //Buttons
        ButtonOutputData restart = UIFactory.ButtonFactory("Restart Level", 150, 300, 500, 80, FOREST_GREEN);
        ButtonOutputData back = UIFactory.ButtonFactory("Back to Select", 150, 400, 500, 80, FOREST_GREEN);

        restart.setButtonCallback(LevelResetInteractor::execute);
        back.setButtonCallback(LoadLevelSelectInteractor::execute);

        scene.addUIElements(new GameUI(restart), new GameUI(back), new GameUI(label));
    }

    public static void createPauseOverlay(Scene scene) {
        // Label
        LabelOutputData label = UIFactory.LabelFactory("Game Paused", 125, 40, 550, 200, LIGHT_GREEN);

        //Buttons
        ButtonOutputData resume = UIFactory.ButtonFactory("Resume Game", 250, 300, 300, 80, LIGHT_GREEN);
        ButtonOutputData restart = UIFactory.ButtonFactory("Restart Game", 240, 400, 320, 80, LIGHT_GREEN);
        ButtonOutputData exit = UIFactory.ButtonFactory("Exit Game", 265, 500, 270, 80, LIGHT_GREEN);
        ButtonOutputData darken_bg = UIFactory.ButtonFactory("", 0, 0, 800, 800, LIGHT_GREEN);
        darken_bg.setImage("ui/darken_bg.png");

        resume.setButtonCallback(GameUnpauseInteractor::execute);
        restart.setButtonCallback(LevelResetInteractor::execute);
        exit.setButtonCallback(LoadLevelSelectInteractor::execute);

        scene.addUIElements(new GameUI(resume),
                new GameUI(restart),
                new GameUI(exit),
                new GameUI(label),
                new GameUI(darken_bg));

    }


    public static LabelOutputData LabelFactory(String text, int x, int y, int width, int height) {
        LabelOutputData label = new LabelOutputData(text, x, y, width, height);
        label.getComponent().setForeground(Color.decode(FOREST_GREEN));

        return label;
    }

    public static LabelOutputData DeathLabelFactory(int x, int y, int width, int height) {
        LabelOutputData label = LabelFactory(DEATH_COUNT, x, y, width, height);
        label.setFancyFontSize(45);
        deathCounter = label;

        return label;
    }

    public static LabelOutputData LabelFactory(String text, int x, int y, int width, int height, String color) {
        LabelOutputData label = new LabelOutputData(text, x, y, width, height);
        label.getComponent().setForeground(Color.decode(color));

        if (color.equals(LIGHT_GREEN)) {
            PausedElements.add(new GameUI(label));
        }

        return label;
    }

    public static ButtonOutputData ButtonFactory(String text, int x, int y, int width, int height) {
        ButtonOutputData button = new ButtonOutputData(text, x, y, width, height);
        button.getComponent().setForeground(Color.decode(FOREST_GREEN));
        button.setTransparent(true);

        return button;
    }

    public static ButtonOutputData ButtonFactory(String text, int x, int y, int width, int height, String color) {
        ButtonOutputData button = new ButtonOutputData(text, x, y, width, height);
        button.getComponent().setForeground(Color.decode(color));
        button.setTransparent(true);

        if (color.equals(LIGHT_GREEN)) {
            PausedElements.add(new GameUI(button));
        }

        return button;
    }

    public static void removePause(Scene scene) {
        for (int i = 0; i < PausedElements.size(); i++) {
            Object obj = PausedElements.get(i);
            if (obj instanceof GameUI) {
                scene.removeUIElement((GameUI) obj);
            } else {
                scene.removeSceneObject((GameObject) obj);
            }
        }
    }

}