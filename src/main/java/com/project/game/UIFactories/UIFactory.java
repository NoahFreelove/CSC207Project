package com.project.game.UIFactories;


import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.UI.*;
import com.project.game.Scripts.PlayerDeath;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

import static com.project.engine.UI.UIConstants.*;

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
        GameUILabel label = new GameUILabel(ORIGINAL_LIVES_TEXT, x, y, width, height);
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

    public static GameUIButton ButtonFactory(String text, int x, int y, int width, int height) {
        GameUIButton button = new GameUIButton(text, x, y, width, height);
        button.setFont(FontCreator.createFont(BUTTON_SIZE));
        button.setForeground(Color.decode(FOREST_GREEN));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setTransparent(true);

        return button;
    }

    public static GameUIButton ButtonFactory(String text, int x, int y, int width, int height, String color) {
        GameUIButton button = new GameUIButton(text, x, y, width, height);
        button.setFont(FontCreator.createFont(BUTTON_SIZE));
        button.setForeground(Color.decode(color));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setTransparent(true);
        if (color == LIGHT_GREEN) {
            PausedElements.add(button);
        }

        return button;
    }



    public static void addToPause(Scene scene) {
        for (int i = 0; i < PausedElements.size(); i++) {
            Object obj = PausedElements.get(i);
            if (obj instanceof JComponent) {
                scene.addUIElement((JComponent) obj);
            } else {
                scene.addSceneObject((GameObject) obj);
            }
        }
    }

    public static void removePause(Scene scene) {
        for (int i = 0; i < PausedElements.size(); i++) {
            Object obj = PausedElements.get(i);
            if (obj instanceof JComponent) {
                scene.removeUIElement((JComponent) obj);
            } else {
                scene.removeSceneObject((GameObject) obj);
            }
        }
    }

    public static void updateDeathCount() {
        deathCounter.setText("Lives: " + (LIVES - PlayerDeath.getDeathCount()));
    }
}
