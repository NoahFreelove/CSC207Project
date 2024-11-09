package com.project.game.editor;

import com.project.engine.Core.Engine;
import com.project.engine.Core.Scene;
import com.project.engine.EngineMain;
import com.project.engine.Core.GameObject;

import java.util.ArrayList;

public class LevelEditor extends Scene {

    private final ArrayList<EditorObjectStruct> tiles = new ArrayList<>();

    public int selectedTileType = 0;

    public LevelEditor() {
        GameObject cameraController = new GameObject();
        cameraController.getTransform().translate(-190d, 25d);
        cameraController.addBehavior(new CameraController());
        cameraController.addBehavior(getCamera());
        addSceneObject(cameraController, true);

        addTile(0, 256, 256, 1, 1);
        setScaleX(2);
        setScaleY(2);
    }

    public void addTile(int ID, int x, int y, int scaleX, int scaleY) {
        boolean[] exists = new boolean[1];
        boolean[] playerExists = new boolean[1];
        tiles.forEach(editorObjectStruct -> {
            if (editorObjectStruct.xPos == x && editorObjectStruct.yPos == y) {
                exists[0] = true;
            }

            if (editorObjectStruct.ID == 0)
                playerExists[0] = true;
        });

        if (exists[0]) {
            return;
        }

        if (ID == 0 && playerExists[0]) {
            return;
        }

        GameObject go = new GameObject();
        EditorObjectStruct eos = new EditorObjectStruct(ID, go, x, y, scaleX, scaleY);
        tiles.add(eos);
        addSceneObject(go);
    }


    public void removeTile(int xPos, int yPos) {
        GameObject foundRef = tiles.stream().filter(eos -> eos.xPos == xPos && eos.yPos == yPos).findFirst().map(eos -> eos.linkedObject).orElse(null);
        if (foundRef == null)
            return;

        removeSceneObject(foundRef);

        tiles.removeIf(editorObjectStruct -> editorObjectStruct.linkedObject.equals(foundRef));
    }

    

    public static void loadFromFile(String path) {

    }

    public static void saveToFile(String path) {

    }

    public static void exportToFile(String path) {

    }

    public void exitLevelEditor() {
        Engine.getInstance().getPrimaryWindow().setWindowSizeForce(800, 800);
        EngineMain.loadTestScene();
    }

}