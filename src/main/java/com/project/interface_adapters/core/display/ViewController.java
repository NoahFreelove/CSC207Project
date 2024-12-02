package com.project.interface_adapters.core.display;

import com.project.use_cases.core.game.GameInteractor;

import java.awt.*;

public class ViewController {
    public ViewController() {

    }

    public void queueRedraw(Graphics2D g2d) {
        GameInteractor e = GameInteractor.getInstance();
        e.render(e.getPrimaryWindow().getActiveScene(), g2d);
    }
}
