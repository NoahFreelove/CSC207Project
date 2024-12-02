package com.project.use_cases.load_level;

import com.project.entity.core.GameObject;
import com.project.entity.core.Scene;
import com.project.entity.ui.GameUI;
import com.project.use_cases.core.editor.EditorObjectStruct;
import com.project.use_cases.core.editor.LevelEditor;
import com.project.use_cases.core.game.GameInteractor;
import com.project.use_cases.core.game.GameOutputData;
import com.project.use_cases.core.prebuilts.game_objects.*;
import com.project.use_cases.core.prebuilts.game_objects.game_object_types.ObjectType;
import com.project.use_cases.core.prebuilts.scenes.LevelSelectionFactory;
import com.project.use_cases.core.prebuilts.scripts.WinScript;

import java.util.List;

public class LoadLevelInteractor {
    private static LevelEditor instance;

    private static void convertTilesToRealObjects(boolean testing, Scene output, LevelEditor editor) {
        GroundBlockFactory groundBlockFactory = (GroundBlockFactory) AbstractObjectFactory.makeFactory(ObjectType.GROUND_BLOCK);
        SpikeFactory spikeFactory = (SpikeFactory) AbstractObjectFactory.makeFactory(ObjectType.SPIKE);
        ItemBlockFactory itemBlockFactory = (ItemBlockFactory) AbstractObjectFactory.makeFactory(ObjectType.ITEM_BLOCK);
        HiddenSpikeFactory hiddenSpikeFactory = (HiddenSpikeFactory) AbstractObjectFactory.makeFactory(ObjectType.HIDDEN_SPIKE);
        HiddenBlockFactory hiddenBlockFactory = (HiddenBlockFactory) AbstractObjectFactory.makeFactory(ObjectType.HIDDEN_BLOCK);
        CloudFactory cloudFactory = (CloudFactory) AbstractObjectFactory.makeFactory(ObjectType.CLOUD);
        EnemyCloudFactory enemyCloudFactory = (EnemyCloudFactory) AbstractObjectFactory.makeFactory(ObjectType.CLOUD_ENEMY);
        EnemyFactory enemyFactory = (EnemyFactory) AbstractObjectFactory.makeFactory(ObjectType.ENEMY);
        IceBlockFactory iceBlockFactory = (IceBlockFactory) AbstractObjectFactory.makeFactory(ObjectType.ICE_BLOCK);
        MovePlatformFactory movePlatformFactory = (MovePlatformFactory) AbstractObjectFactory.makeFactory(ObjectType.MOVEMENT_PLATFORM);
        CheckpointFactory checkpointFactory = (CheckpointFactory) AbstractObjectFactory.makeFactory(ObjectType.CHECKPOINT);

        for (EditorObjectStruct obj : editor.getTiles()) {
            GameObject out = null;
            switch (obj.ID) {
                case 0: {
                    out = PlayerFactory.makeFactory(ObjectType.PLAYER)
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY()-33, 10, obj.scaleX, obj.scaleY);

                    output.getCamera().update(out, 0);
                    output.getCamera().setOffsetX(-100);
                    output.getCamera().setOffsetY(64);

                    output.getCamera().setFollowY(false);
                    output.addSceneObject(out, true);
                    continue;
                }
                case 1: {
                    out = groundBlockFactory
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY(), 3, obj.scaleX, obj.scaleY);
                    break;
                }
                case 2: {
                    out = groundBlockFactory
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY(), 4, obj.scaleX, obj.scaleY, "assets/brick.png");
                    break;
                }
                case 3: {
                    out = itemBlockFactory
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY(), 5, obj.scaleX, obj.scaleY);
                    break;
                }
                case 4: {
                    out = spikeFactory
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY(), 6, obj.scaleX, obj.scaleY);
                    break;
                }
                case 5: {
                    out = hiddenSpikeFactory
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY()+64, 2, obj.scaleX, obj.scaleY);
                    break;
                }
                case 6: {
                    out = hiddenBlockFactory
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY(), 5, obj.scaleX, obj.scaleY);
                    break;
                }
                case 7: {
                    out = PrincessFactory.makeFactory(ObjectType.PRINCESS)
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY()-16, 12, obj.scaleX, obj.scaleY);
                    if(testing) {
                        WinScript w = out.getScriptable(WinScript.class);
                        w.overrideWinEvent = true;
                        w.r = () -> {
                            GameOutputData w1 = GameInteractor.getInstance().getPrimaryWindow();
                            w1.setWindowSizeForce(LevelEditor.levelEditorScreenSize.getFirst(), LevelEditor.levelEditorScreenSize.getSecond());
                            w1.setActiveScene(editor);
                        };
                    }
                    break;
                }
                case 8: {
                    out = cloudFactory
                            .generate(obj.linkedObject.getTransform().getPositionX() - /*center cloud*/ (128*3/2f - 32), obj.linkedObject.getTransform().getPositionY() - (128*1.5/2 -32), 3,3, 1.5);
                    break;
                }
                case 9: {
                    out = enemyCloudFactory
                            .generate(obj.linkedObject.getTransform().getPositionX()- (128*3/2f - 32), obj.linkedObject.getTransform().getPositionY() - (128*1.5/2 -32), 3,3, 1.5);
                    break;
                }
                case 10: {
                    out = enemyFactory
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY(), 10, obj.scaleX, obj.scaleY);
                    break;
                }
                case 11: {
                    out = iceBlockFactory
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY(), 5, obj.scaleX, obj.scaleY);
                    break;
                }
                case 12: {
                    out = movePlatformFactory
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY(), 5, obj.scaleX, obj.scaleY);
                    break;
                }
                case 13: {
                    out = checkpointFactory
                            .generate(obj.linkedObject.getTransform().getPositionX(), obj.linkedObject.getTransform().getPositionY(), 5, obj.scaleX, obj.scaleY);
                    break;
                }
                default: {
                    System.err.println("Unknown level editor export ID: " + obj.ID);
                    continue;
                }
            }

            out.getTransform().setRotation(obj.rot);

            output.addSceneObject(out);
        }
    }

    public static Scene loadLevelScene(boolean testing, LevelEditor editor) {
        Scene output = new Scene();
        convertTilesToRealObjects(testing, output, editor);

        output.setScaleX(1.25f);
        output.setScaleY(1.25f);

        output.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.BOUNDARY, 0, 0, 5, 800));
        output.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.BACKGROUND, 0, -128, 0));
        output.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.VOID));
        output.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.ESCAPE_KEY), true);
        output.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.LOADING, 0, 0, 1000, 1/ output.getScaleX(), 1/ output.getScaleY()));

        instance = editor;
        return output;
    }

    public static LevelEditor getLevelEditor() {
        return instance;
    }

    public static void execute(String abs) {
        LevelEditor le = new LevelEditor();
        List<GameUI> uis = le.getUIElements();

        for(GameUI ui : uis) {
            le.removeUIElement(ui);
        }

        le.loadFromFile(abs, !LevelSelectionFactory.isInEditor);

        GameOutputData w = GameInteractor.getInstance().getPrimaryWindow();

        w.refocusInWindow();
        System.gc();
        Scene out = loadLevelScene(false, le);

        w.setActiveScene(out);
    }
}