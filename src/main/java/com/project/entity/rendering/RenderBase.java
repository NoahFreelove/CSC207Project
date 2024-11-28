package com.project.entity.rendering;

import com.project.entity.scripting.IScriptable;
import com.project.entity.core.GameObject;
import com.project.entity.core.Scene;

import java.awt.*;

/**
 * How does this work?
 * Its really just abstract to force the implementation of the renderComponent method.
 * This is the base class for all renderers.
 */
public abstract class RenderBase implements IRenderable, IScriptable {

    public abstract void render(GameObject attached, Scene scene, Graphics2D g2d);

}
