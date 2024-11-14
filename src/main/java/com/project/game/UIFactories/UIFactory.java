package com.project.game.UIFactories;


import com.project.engine.Core.Scene;
import com.project.engine.UI.FontCreator;
import com.project.engine.UI.GameUI;
import com.project.engine.UI.GameUIButton;
import com.project.engine.UI.GameUILabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static com.project.engine.UI.UIConstants.*;

public class UIFactory {
    public static ArrayList<JComponent> UIElements = new ArrayList<>();

    public static GameUILabel LabelFactory(String text, int x, int y, int width, int height) {
        GameUILabel label = new GameUILabel(text, x, y, width, height);
        label.setFont(FontCreator.createFont(TITLE_SIZE));
        label.setForeground(Color.decode(FOREST_GREEN));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        UIElements.add(label);

        return label;
    }

    public static GameUIButton ButtonFactory(String text, int x, int y, int width, int height) {
        GameUIButton button = new GameUIButton(text, x, y, width, height);
        button.setFont(FontCreator.createFont(BUTTON_SIZE));
        button.setForeground(Color.decode(FOREST_GREEN));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setTransparent(true);
        UIElements.add(button);

        return button;
    }

    public static void addToScene(Scene scene) {
        for (int i = 0; i < UIElements.size(); i++) {
            scene.addUIElement(UIElements.get(i));
        }
        UIElements.clear();
    }
}
