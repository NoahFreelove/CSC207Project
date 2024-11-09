package com.project.engine.Scenes;

import com.project.engine.UI.GameUIButton;


public class PlayButton extends GameUIButton {


    public PlayButton() {
        super();
    }

    public PlayButton(int x, int y, int width, int height) {
        super("Play", x, y, width, height);
    }

    @Override
    public void onClick() {
        // TODO: make it play the game
    }
}
