package com.project.entity.rendering;

import com.project.entity.core.GameObject;
import com.project.entity.core.Scene;
import com.project.entity.serialization.ISerializable;

import java.awt.*;

public interface IRenderable extends ISerializable {

    /**
     * Do NOT return a new object every frame! Simply mutate the same object.
     * We don't want to be using 200mb memory!
     * @return A formatted JComponent fit for rendering
     */
    void render(GameObject attached, Scene scene, Graphics2D g2d);
}
