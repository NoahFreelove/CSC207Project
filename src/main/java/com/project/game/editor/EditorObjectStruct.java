package com.project.game.editor;

import com.project.engine.Core.GameObject;
import com.project.game.TileCache;
import com.project.engine.Rendering.SpriteRenderer;
import org.json.JSONObject;

public class EditorObjectStruct {

    public final int ID;
    public final GameObject linkedObject;

    public int scaleX;
    public int scaleY;

    public int xPos;
    public int yPos;

    private void initRenderer() {
        SpriteRenderer sr = new SpriteRenderer();
        sr.setImage(TileCache.spriteCache.get(ID), TileCache.BASE_TILE_SIZE, TileCache.BASE_TILE_SIZE);
        linkedObject.getTransform().setPosition(xPos, yPos);
        linkedObject.getTransform().setScaleX(scaleX);
        linkedObject.getTransform().setScaleY(scaleY);
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

    public JSONObject serialize() {
        JSONObject output = new JSONObject();
        output.put("ID", ID);
        output.put("scaleX", scaleX);
        output.put("scaleY", scaleY);
        output.put("xPos", xPos);
        output.put("yPos", yPos);
        return output;
    }

    public EditorObjectStruct deserialize(JSONObject o) {
        return new EditorObjectStruct(o.getInt("ID"), new GameObject(), o.getInt("xPos"), o.getInt("yPos"), o.getInt("scaleX"), o.getInt("scaleY"));
    }
}
