package com.project.engine.Rendering;

import com.project.engine.Core.GameObject;

import javax.swing.*;

public interface IRenderable {

    /**
     * Do NOT return a new object every frame! Simply mutate the same object.
     * We don't want to be using 200mb memory!
     * @return A formatted JComponent fit for rendering
     */
    JComponent renderComponent(GameObject attached);
}
