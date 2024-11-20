package com.project.game.editor;

import com.project.engine.Core.GameObject;
import com.project.engine.Rendering.SpriteRenderer;
import org.json.JSONObject;

public class EditorObjectStruct {

    public final int ID;
    public final GameObject linkedObject;

    public double scaleX;
    public double scaleY;

    public int rot;

    public int xPos;
    public int yPos;

    private void initRenderer() {
        SpriteRenderer sr = new SpriteRenderer();
        sr.setImage(EditorTileCache.spriteCache.get(ID), EditorTileCache.BASE_TILE_SIZE, EditorTileCache.BASE_TILE_SIZE);
        linkedObject.getTransform().setPosition(xPos, yPos);
        linkedObject.getTransform().setScaleX(scaleX);
        linkedObject.getTransform().setScaleY(scaleY);
        linkedObject.getTransform().setRotation(rot);
        linkedObject.addRenderable(sr);
    }

    public EditorObjectStruct(int ID, GameObject linkedObject) {
        this.ID = ID;
        this.linkedObject = linkedObject;
        initRenderer();
    }

    public EditorObjectStruct(int ID, GameObject linkedObject, int xPos, int yPos) {
        this.ID = ID;
        this.linkedObject = linkedObject;
        this.xPos = xPos;
        this.yPos = yPos;
        initRenderer();
    }

    public EditorObjectStruct(int ID, GameObject linkedObject, int xPos, int yPos, int scaleX, int scaleY) {
        this.ID = ID;
        this.linkedObject = linkedObject;
        this.xPos = xPos;
        this.yPos = yPos;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        initRenderer();
    }

    public EditorObjectStruct(int ID, GameObject linkedObject, int xPos, int yPos, double scaleX, double scaleY, int rot) {
        this.ID = ID;
        this.linkedObject = linkedObject;
        this.xPos = xPos;
        this.yPos = yPos;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.rot = rot;
        initRenderer();
    }

    public JSONObject serialize() {
        JSONObject output = new JSONObject();
        output.put("ID", ID);
        output.put("scaleX", scaleX);
        output.put("scaleY", scaleY);
        output.put("xPos", xPos);
        output.put("yPos", yPos);
        output.put("rot", rot);
        return output;
    }

    public static EditorObjectStruct deserialize(JSONObject o) {
        return new EditorObjectStruct(o.getInt("ID"), new GameObject(),
                o.getInt("xPos"), o.getInt("yPos"),
                o.getInt("scaleX"), o.getInt("scaleY"),
                o.getInt("rot"));
    }
}
