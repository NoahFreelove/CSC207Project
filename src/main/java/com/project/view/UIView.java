package com.project.view;

import javax.swing.*;

public class UIView extends JPanel {
    public UIView(int width, int height) {
        super();
        this.setBounds(0, 0, width, height);
        this.setOpaque(false);
        this.setLayout(null);
    }

    public void addComponent(JComponent component) {
        this.add(component);
        refresh();
    }

    public void removeComponent(JComponent component) {
        this.remove(component);
        refresh();
    }

    public void refresh() {
        this.revalidate();
        this.repaint();
    }
}
