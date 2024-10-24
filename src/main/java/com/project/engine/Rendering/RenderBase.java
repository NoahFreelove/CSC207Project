package com.project.engine.Rendering;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Tuple;
import com.project.engine.Scripting.IScriptable;

import javax.swing.*;

public abstract class RenderBase implements IRenderable, IScriptable {
    private JComponent component;
    private int xPos;
    private int yPos;
    private int xSize;
    private int ySize;

    public RenderBase(){
        this.component = new JComponent() {};
    }

    public RenderBase(JComponent component){
        this.component = component;
    }
    
    protected void setX(int x){
        this.xPos = x;
    }
    
    protected void setY(int y){
        this.yPos = y;
    }
    
    protected void setPosition(Tuple<Double, Double> position) {
        this.xPos = position.getFirst().intValue();
        this.yPos = position.getSecond().intValue();
    }

    protected void setPosition(double x, double y) {
        this.xPos = (int)x;
        this.yPos = (int)y;
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

    @Override
    public JComponent renderComponent(GameObject attached, Scene scene) {
        double cameraX = scene.getCamera().getCameraX();
        double cameraY = scene.getCamera().getCameraY();
        this.component.setBounds(xPos - (int)cameraX, yPos - (int)cameraY, Math.abs(xSize), Math.abs(ySize));
        return component;
    }
}
