package com.project.entity.ui;

import com.project.entity.serialization.ISerializable;

public class GameUI {
    private GameUIObject underlyingUI;

    public GameUI(GameUIObject underlyingUI) {
        this.underlyingUI = underlyingUI;
    }

    public GameUIObject getUnderlyingUI() {
        return underlyingUI;
    }
}
