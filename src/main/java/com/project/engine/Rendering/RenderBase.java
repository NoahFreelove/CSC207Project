package com.project.engine.Rendering;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Tuple;
import com.project.engine.Scripting.IScriptable;

import javax.swing.*;
import java.awt.*;

/**
 * How does this worK? Figure it out!
 * Its really just abstract to force the implementation of the renderComponent method.
 * This is the base class for all renderers.
 */
public abstract class RenderBase implements IRenderable, IScriptable {
    private JComponent component;
    private int xSize;
    private int ySize;

    public RenderBase(){
        this.component = new JComponent() {};
    }

    public RenderBase(JComponent component){
        this.component = component;
    }

    protected int getWidth(){
        return xSize;
    }

    protected int getHeight(){
        return ySize;
    }

    protected void setWidth(int width) {
        setSize(width, ySize);
    }
    
    protected void setHeight(int height) {
        setSize(xSize, height);
    }
    
    protected void setSize(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public void setBaseComponent(JComponent component){
        this.component = component;
        this.component.setLayout(null);
    }

    public abstract void renderComponent(GameObject attached, Scene scene, Graphics2D g2d);

}
