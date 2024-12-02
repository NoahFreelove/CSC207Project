package com.project.view.core.ui;

import com.project.interface_adapters.core.display.ui.panel.PanelViewManager;

import javax.swing.*;
import java.awt.*;

public class UIPanelView extends JPanel implements PanelViewManager {
    public UIPanelView() {
        super();

        //setLayout(null);
        setOpaque(true);
    }

    public UIPanelView(int x, int y, int width, int height) {
        super();
        this.setBounds(x, y, width, height);
        //setLayout(null);
        setOpaque(true);
    }

    public void setBackground(String hex) {
        super.setBackground(Color.decode(hex));
    }

}
