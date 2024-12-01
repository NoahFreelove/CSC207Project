package com.project.use_cases.prebuilts.game_ui;


import com.project.entity.core.GameObject;
import com.project.entity.core.Scene;
import com.project.entity.ui.*;
import com.project.external_interfaces.FontCreator;
import com.project.use_cases.ui.button.ButtonOutputData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static com.project.entity.ui.UIConstants.*;

public class UIFactory {
    public static ArrayList<Object> PausedElements = new ArrayList<>();
    public static GameUILabel deathCounter;

    public static GameUILabel LabelFactory(String text, int x, int y, int width, int height) {
        GameUILabel label = new GameUILabel(text, x, y, width, height);
        label.setFont(FontCreator.createFont(TITLE_SIZE));
        label.setForeground(Color.decode(FOREST_GREEN));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        return label;
    }

    public static GameUILabel DeathLabelFactory(int x, int y, int width, int height) {
        GameUILabel label = new GameUILabel(DEATH_COUNT, x, y, width, height);
        label.setFont(FontCreator.createFont(COUNTER_SIZE));
        label.setForeground(Color.decode(FOREST_GREEN));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        deathCounter = label;

        return label;
    }

    public static GameUILabel LabelFactory(String text, int x, int y, int width, int height, String color) {
        GameUILabel label = new GameUILabel(text, x, y, width, height);
        label.setFont(FontCreator.createFont(TITLE_SIZE));
        label.setForeground(Color.decode(color));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        if (color == LIGHT_GREEN) {
            PausedElements.add(label);
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

        if (color == LIGHT_GREEN) {
            PausedElements.add(button);
        }

        return button;
    }

    public static void removePause(Scene scene) {
        for (int i = 0; i < PausedElements.size(); i++) {
            Object obj = PausedElements.get(i);
            if (obj instanceof JComponent) {
                scene.removeUIElement((GameUI) obj);
            } else {
                scene.removeSceneObject((GameObject) obj);
            }
        }
    }

}
