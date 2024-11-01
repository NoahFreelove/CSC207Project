package com.project.engine.Rendering;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;

import javax.swing.*;
import java.awt.*;

public interface IRenderable {

    /**
     * Do NOT return a new object every frame! Simply mutate the same object.
     * We don't want to be using 200mb memory!
     * @return A formatted JComponent fit for rendering
     */
    void renderComponent(GameObject attached, Scene scene, Graphics2D g2d);
}
