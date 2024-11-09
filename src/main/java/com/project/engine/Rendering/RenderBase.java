package com.project.engine.Rendering;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Scripting.IScriptable;

import java.awt.*;

/**
 * How does this work?
 * Its really just abstract to force the implementation of the renderComponent method.
 * This is the base class for all renderers.
 */
public abstract class RenderBase implements IRenderable, IScriptable {

    public abstract void render(GameObject attached, Scene scene, Graphics2D g2d);

}
