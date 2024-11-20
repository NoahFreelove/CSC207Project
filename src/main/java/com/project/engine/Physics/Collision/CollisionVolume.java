package com.project.engine.Physics.Collision;

import com.project.engine.Physics.Collision.Types.ECollisionShape;
import com.project.engine.Physics.Collision.Types.ECollisionVolume;

public class CollisionVolume {
    private double xOffset = 0, yOffset = 0;
    private double relWidth = 1, relHeight = 1;


    private String tag = "volume";

    public CollisionVolume() {}

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    public void setOffset(double xOffset, double yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void setRelDimensions(double wRel, double hRel) {
        this.relWidth = wRel;
        this.relHeight = hRel;
    }

    public double getXOffset() {
        return xOffset;
    }

    public double getYOffset() {
        return yOffset;
    }

    public double getRelWidth() {
        return relWidth;
    }

    public double getRelHeight() {
        return relHeight;
    }

    public ECollisionVolume volumeType() { return ECollisionVolume.NONE; }
    public ECollisionShape volumeShape() { return ECollisionShape.NONE; }


}
