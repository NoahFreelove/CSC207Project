package com.project.game.editor;

import com.project.engine.Core.Engine;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.Core.GameObject;
import com.project.engine.IO.FileIO;
import com.project.engine.Input.EInputType;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.IScriptable;
import com.project.engine.UI.GameUIButton;
import com.project.game.ObjectFactories.*;
import com.project.game.Scenes.MainMenuFactory;
import com.project.game.Scenes.PauseOverlayFactory;
import com.project.game.UIFactories.UIFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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
        addGuideLines();

        loadFromFile(FileIO.GetAbsPathOfResource("/levels/level1.json"));
    }

    public void addTile(int ID, int x, int y) {
        if (x < 0 || x >= 500 * 64)
            return;

        if (y >= 640 || y<= 0)
            return;

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
        if(EditorTileCache.disableTransformMutations.getOrDefault(ID, false)) {
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
        tiles.clear();
        addGuideLines();
    }

    public void loadFromFile(String path) {
        activeFile = path;
        for(EditorObjectStruct eos : tiles) {
            removeSceneObject(eos.linkedObject);
        }
        tiles.clear();
        addGuideLines();
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

    public Scene exportToScene(boolean testing) {
        Scene output = new Scene();

        GroundBlockFactory groundBlockFactory = (GroundBlockFactory) AbstractObjectFactory.makeFactory(ObjectType.GROUND_BLOCK);
        SpikeFactory spikeFactory = (SpikeFactory) AbstractObjectFactory.makeFactory(ObjectType.SPIKE);
        ItemBlockFactory itemBlockFactory = (ItemBlockFactory) AbstractObjectFactory.makeFactory(ObjectType.ITEM_BLOCK);
        HiddenSpikeFactory hiddenSpikeFactory = (HiddenSpikeFactory) AbstractObjectFactory.makeFactory(ObjectType.HIDDEN_SPIKE);
        HiddenBlockFactory hiddenBlockFactory = (HiddenBlockFactory) AbstractObjectFactory.makeFactory(ObjectType.HIDDEN_BLOCK);

        for (EditorObjectStruct obj : tiles) {
            GameObject out = null;
            switch (obj.ID) {
                case 0: {
                    out = PlayerFactory.makeFactory(ObjectType.PLAYER)
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY(), 10, obj.scaleX, obj.scaleY);
                    if(testing) {
                        out.addBehavior(new IScriptable() {
                            @Override
                            public void onInput(GameObject parent, String keyName, EInputType inputType, int inputMods) {
                                if (keyName.equals("ESC") && inputType == EInputType.RELEASE) {
                                    GameWindow w = Engine.getInstance().getPrimaryWindow();
                                    if (w == null)
                                        return;
                                    w.setWindowSizeForce(1920,1080);
                                    w.setActiveScene(LevelEditor.this);
                                }
                            }
                        });
                    }
                    output.getCamera().update(out, 0);
                    output.getCamera().setOffsetX(-100);
                    output.getCamera().setOffsetY(100);

                    output.getCamera().setFollowY(false);
                    output.addSceneObject(out, true);
                    continue;
                }
                case 1: {
                    out = groundBlockFactory
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY(), 2, obj.scaleX, obj.scaleY);
                    break;
                }
                case 2: {
                    out = groundBlockFactory
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY(), 2, obj.scaleX, obj.scaleY, "assets/brick.png");
                    break;
                }
                case 3: {
                    out = itemBlockFactory
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY(), 2, obj.scaleX, obj.scaleY);
                    break;
                }
                case 4: {
                    out = spikeFactory
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY(), 2, obj.scaleX, obj.scaleY);
                    break;
                }
                case 5: {
                    out = hiddenSpikeFactory
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY()+64, 2, obj.scaleX, obj.scaleY);
                    break;
                }
                default: {
                    System.err.println("Unknown level editor export ID: " + obj.ID);
                    continue;
                }
            }

            if(!testing){
                GameUIButton pause = UIFactory.ButtonFactory("Pause", 600, 10, 220, 50);

                pause.onClickEvent = PauseOverlayFactory::pauseGame;
                output.addUIElement(pause);
            }
            out.getTransform().setRotation(obj.rot);

            output.addSceneObject(out);

        }
        output.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.BOUNDARY, 0, 0, 5, 800));
        output.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.BACKGROUND));
        GameObject voidObject = AbstractObjectFactory.generateOfType(ObjectType.VOID);
        output.addSceneObject(voidObject);
        output.setScaleX(1.25f);
        output.setScaleY(1.25f);

        return output;
    }

    public void exportToFile(String path) {
        FileIO.WriteTextAbs(path, exportToScene(false).serialize().toString(4));
    }

    public void exitLevelEditor() {
        GameWindow w = Engine.getInstance().getPrimaryWindow();
        if (w == null)
            return;
        w.setWindowSizeForce(800, 800);
        w.setActiveScene(MainMenuFactory.createScene());
    }


    public static LevelGenerationInterface loadFromFileForMainGame(String abs) {
        return () -> {
            LevelEditor le = new LevelEditor();
            le.loadFromFile(abs);
            le.start();
            le.update(0.001d);
            return le.exportToScene(false);
        };
    }

}