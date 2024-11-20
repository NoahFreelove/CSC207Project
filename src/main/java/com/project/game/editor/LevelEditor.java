package com.project.game.editor;

import com.project.engine.Core.Engine;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.Core.GameObject;
import com.project.engine.IO.FileIO;
import com.project.game.ObjectFactories.AbstractObjectFactory;
import com.project.game.ObjectFactories.GroundBlockFactory;
import com.project.game.ObjectFactories.ObjectType;
import com.project.game.ObjectFactories.PlayerFactory;
import com.project.game.Scenes.MainMenuFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.function.Consumer;

public class LevelEditor extends Scene {

    private final ArrayList<EditorObjectStruct> tiles = new ArrayList<>();

    public String activeFile = "";

    public int selectedTileType = 1;

    private double proposedScaleX = 1;
    private double proposedScaleY = 1;
    private int proposedRot = 0;

    public LevelEditor() {
        GameObject cameraController = new GameObject();
        cameraController.getTransform().translate(-190d, 25d);
        cameraController.addBehavior(new EditorCameraController());
        cameraController.addBehavior(getCamera());
        addSceneObject(cameraController, true);

        addTile(0, 256, 256);
        setScaleX(2);
        setScaleY(2);
    }

    public void addTile(int ID, int x, int y) {
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
        if(ID == 0) {
            GameObject go = new GameObject();
            EditorObjectStruct eos = new EditorObjectStruct(ID, go, x, y, 1, 1, 0);
            tiles.add(eos);
            addSceneObject(go);
        }
        else {
            GameObject go = new GameObject();
            EditorObjectStruct eos = new EditorObjectStruct(ID, go, x, y, proposedScaleX, proposedScaleY, proposedRot);
            tiles.add(eos);
            addSceneObject(go);
        }
    }


    public void removeTile(int xPos, int yPos) {
        GameObject foundRef = tiles.stream().filter(eos -> eos.xPos == xPos && eos.yPos == yPos).findFirst().map(eos -> eos.linkedObject).orElse(null);
        if (foundRef == null)
            return;

        removeSceneObject(foundRef);

        tiles.removeIf(editorObjectStruct -> editorObjectStruct.linkedObject.equals(foundRef));
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
    

    public void loadFromFile(String path) {
        activeFile = path;
        for(EditorObjectStruct eos : tiles) {
            removeSceneObject(eos.linkedObject);
        }
        tiles.clear();
        String fileContents = FileIO.ReadTextAbs(path);
        JSONObject read = new JSONObject(fileContents);
        JSONArray tileArray = read.getJSONArray("tiles");
        tileArray.forEach(o -> {
            JSONObject obj = (JSONObject) o;
            EditorObjectStruct eos = EditorObjectStruct.deserialize(obj);
            tiles.add(eos);
            addSceneObject(eos.linkedObject);
        });

    }

    public void saveToFile() {
        if(activeFile.isEmpty())
            return;

        JSONObject output = new JSONObject();
        JSONObject metadata = new JSONObject();
        output.put("meta", metadata);
        JSONArray outTiles = new JSONArray();
        for (EditorObjectStruct obj : tiles) {
            outTiles.put(obj.serialize());
        }

        output.put("tiles", outTiles);

        FileIO.WriteTextAbs(activeFile, output.toString(4));
    }

    public Scene exportToScene() {
        Scene output = new Scene();

        GroundBlockFactory groundBlockFactory = (GroundBlockFactory) AbstractObjectFactory.makeFactory(ObjectType.GROUND_BLOCK);

        for (EditorObjectStruct obj : tiles) {
            GameObject out = null;
            switch (obj.ID) {
                case 0: {
                    out = PlayerFactory.makeFactory(ObjectType.PLAYER)
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY(), 10, obj.scaleX, obj.scaleY);
                    out.getTransform().setRotation(obj.rot);
                    break;
                }
                case 1: {
                    out = groundBlockFactory
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY(), 2, obj.scaleX, obj.scaleY);
                    out.getTransform().setRotation(obj.rot);
                    break;
                }
                default: {
                    System.err.println("Unknown level editor export ID: " + obj.ID);
                    continue;
                }
            }
            output.addSceneObject(out);
            output.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.BACKGROUND));
        }

        return output;
    }

    public void exportToFile(String path) {
        FileIO.WriteTextAbs(path, exportToScene().serialize().toString(4));
    }

    public void exitLevelEditor() {
        GameWindow w = Engine.getInstance().getPrimaryWindow();
        if (w == null)
            return;
        w.setWindowSizeForce(800, 800);
        w.setActiveScene(MainMenuFactory.createScene());
    }

}