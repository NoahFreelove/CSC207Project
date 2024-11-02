package com.project.engine.Rendering;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Serialization.ISerializable;

import java.awt.*;

public interface IRenderable extends ISerializable {

    /**
     * Do NOT return a new object every frame! Simply mutate the same object.
     * We don't want to be using 200mb memory!
     * @return A formatted JComponent fit for rendering
     */
    void render(GameObject attached, Scene scene, Graphics2D g2d);
}
