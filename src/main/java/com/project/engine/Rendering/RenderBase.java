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
    
    protected void setPosition(Tuple<Double, Double> position){
        this.xPos = position.getFirst().intValue();
        this.yPos = position.getSecond().intValue();
    }

    protected void setPosition(double x, double y){
        this.xPos = (int)x;
        this.yPos = (int)y;
    }
    
    protected void setWidth(int width){
        this.xSize = width;
    }
    
    protected void setHeight(int height){
        this.ySize = height;
    }
    
    protected void setSize(Tuple<Double, Double> size){
       this.xSize = size.getFirst().intValue();
       this.ySize = size.getSecond().intValue();
    }

    public void setBaseComponent(JComponent component){
        this.component = component;
        this.component.setLayout(null);
    }

    @Override
    public JComponent renderComponent(GameObject attached, Scene scene) {
        double cameraX = scene.getCamera().getCameraX();
        double cameraY = scene.getCamera().getCameraY();
        this.component.setBounds(xPos - (int)cameraX, yPos - (int)cameraY, xSize, ySize);
        return component;
    }
}
