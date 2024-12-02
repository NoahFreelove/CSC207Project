package com.project.entity.windowing;

import com.project.entity.ui.GameUI;

import javax.swing.*;

public interface WindowUICallback {
    void addUIComponent(GameUI component);
    void removeUIComponent(GameUI component);
}
