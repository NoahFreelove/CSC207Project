package com.project.engine.Scenes;

import com.project.engine.UI.GameUIButton;

public class ExitButton extends GameUIButton {
    public ExitButton() {
        super();
    }

    public ExitButton(int x, int y, int width, int height) {
        super("Leave Game", x, y, width, height);
    }

    @Override
    public void onClick() {
        System.exit(0);
    }
}
