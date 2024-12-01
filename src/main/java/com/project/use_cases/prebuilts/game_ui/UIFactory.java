package com.project.use_cases.prebuilts.game_ui;


import com.project.entity.core.GameObject;
import com.project.entity.core.Scene;
import com.project.entity.ui.*;
import com.project.use_cases.ui.button.ButtonOutputData;
import com.project.use_cases.ui.label.LabelOutputData;

import java.awt.*;
import java.util.ArrayList;

import static com.project.use_cases.prebuilts.game_ui.UIConstants.*;

public class UIFactory {
    public static ArrayList<Object> PausedElements = new ArrayList<>();
    public static LabelOutputData deathCounter;

    public static LabelOutputData LabelFactory(String text, int x, int y, int width, int height) {
        LabelOutputData label = new LabelOutputData(text, x, y, width, height);
        label.getComponent().setForeground(Color.decode(FOREST_GREEN));

        return label;
    }

    public static LabelOutputData DeathLabelFactory(int x, int y, int width, int height) {
        LabelOutputData label = new LabelOutputData(DEATH_COUNT, x, y, width, height);
        label.getComponent().setForeground(Color.decode(FOREST_GREEN));
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
