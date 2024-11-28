package com.project.entity.windowing;

import java.awt.*;

/**
 * Needed because tab will try to select the next component in the window, which is not what we want.
 */
public class NoFocusTraversalPolicy extends FocusTraversalPolicy {
    @Override
    public Component getComponentAfter(Container aContainer, Component aComponent) {
        return null;
    }

    @Override
    public Component getComponentBefore(Container aContainer, Component aComponent) {
        return null;
    }

    @Override
    public Component getFirstComponent(Container aContainer) {
        return null;
    }

    @Override
    public Component getLastComponent(Container aContainer) {
        return null;
    }

    @Override
    public Component getDefaultComponent(Container aContainer) {
        return null;
    }
}