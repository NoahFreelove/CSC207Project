package com.project.use_cases.core.editor;

import com.project.entity.core.Scene;
import com.project.entity.core.Tuple;
import com.project.entity.core.GameObject;
import com.project.external_interfaces.core.FileIO;
import com.project.entity.rendering.SpriteRenderer;
import com.project.use_cases.editor.EditorTileAddInteractor;
import com.project.use_cases.editor.EditorTileRemoveInteractor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LevelEditor extends Scene {

    private final ArrayList<EditorObjectStruct> tiles = new ArrayList<>();

    public static final Tuple<Integer, Integer> levelEditorScreenSize = new Tuple<>((int)(1920*0.8),(int)(1080*0.8));

    public String activeFile = "";

    public int selectedTileType = 1;

    private double proposedScaleX = 1;
    private double proposedScaleY = 1;
    private int proposedRot = 0;

    private JSONObject metadata = new JSONObject();

    public LevelEditor() {
        GameObject cameraController = new GameObject();
        cameraController.getTransform().translate(-190d, 25d);
        cameraController.addBehavior(new EditorCamera());
        cameraController.addBehavior(getCamera());
        addSceneObject(cameraController, true);

        addTile(0, 256, 256);
        setScaleX(2);
        setScaleY(2);
        addGuideLines();
        newFile();
    }

    public void addTile(int ID, int x, int y) {
        EditorTileAddInteractor.execute(this, ID, x, y, proposedScaleX, proposedScaleY, proposedRot);
    }

    public ArrayList<EditorObjectStruct> getTiles() {
        return tiles;
    }

    public void removeTile(int xPos, int yPos) {
        EditorTileRemoveInteractor.execute(this, xPos, yPos);
    }


    public void modifyTile(double scaleX, double scaleY, double rot) {
        if (scaleX > 0 ){
            proposedScaleX = scaleX;
        }
        if (scaleY > 0) {
            proposedScaleY = scaleY;
        }
        if (rot >= 0 && rot <= 270 && rot % 90 == 0) {
            proposedRot = (int) rot;
        }
    }

    private void addGuideLines() {
        int size = 500 * 64;
        GameObject xGuide = new GameObject();
        xGuide.getTransform().setWidth(800);
        xGuide.getTransform().setHeight(1);
        xGuide.getTransform().translate(-64, 640);
        SpriteRenderer xGuideRenderer = new SpriteRenderer("assets/red.jpg", size, 64);
        xGuideRenderer.setIndependentOfCamera(false);
        xGuide.addRenderable(xGuideRenderer);
        addSceneObject(xGuide);

        GameObject xGuide2 = new GameObject();
        xGuide2.getTransform().setWidth(800);
        xGuide2.getTransform().setHeight(1);
        xGuide2.getTransform().translate(-64, 0);
        SpriteRenderer xGuide2Renderer = new SpriteRenderer("assets/red.jpg", size, 64);
        xGuide2Renderer.setIndependentOfCamera(false);
        xGuide2.addRenderable(xGuide2Renderer);
        addSceneObject(xGuide2);

        GameObject yGuide = new GameObject();
        yGuide.getTransform().setWidth(1);
        yGuide.getTransform().setHeight(800);
        yGuide.getTransform().translate(-64, 0);
        SpriteRenderer yGuideRenderer = new SpriteRenderer("assets/red.jpg", 64, 640);
        yGuideRenderer.setIndependentOfCamera(false);
        yGuide.addRenderable(yGuideRenderer);
        addSceneObject(yGuide);
    }

    public void newFile(){
        activeFile = "";

        for(EditorObjectStruct eos : tiles) {
            removeSceneObject(eos.linkedObject);
        }

        metadata = new JSONObject();
        tiles.clear();
        addGuideLines();
    }

    public void loadFromFile(String path, boolean relative) {
        activeFile = path;

        for(EditorObjectStruct eos : tiles) {
            removeSceneObject(eos.linkedObject);
        }

        tiles.clear();
        addGuideLines();
        String fileContents = (relative)? FileIO.ReadText(path) : FileIO.ReadTextAbs(path);
        JSONObject read = new JSONObject(fileContents);
        metadata = read.getJSONObject("meta");
        JSONArray tileArray = read.getJSONArray("tiles");
        tileArray.forEach(o -> {
            JSONObject obj = (JSONObject) o;
            EditorObjectStruct eos = EditorObjectStruct.deserialize(obj);
            tiles.add(eos);
            addSceneObject(eos.linkedObject);
        });
        start();
        update(0.1);
    }

    public void saveToFile() {
        if(activeFile.isEmpty())
            return;

        JSONObject output = new JSONObject();
        output.put("meta", metadata);
        JSONArray outTiles = new JSONArray();
        for (EditorObjectStruct obj : tiles) {
            outTiles.put(obj.serialize());
        }

        output.put("tiles", outTiles);

        FileIO.WriteTextAbs(activeFile, output.toString(4));
    }
}