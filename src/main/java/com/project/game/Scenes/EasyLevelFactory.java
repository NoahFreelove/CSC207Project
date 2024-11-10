package com.project.game.Scenes;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.WindowStatsDebug;
import com.project.game.ObjectFactories.HiddenBlockFactory;
import com.project.game.ObjectFactories.HiddenSpikeFactory;
import com.project.game.ObjectFactories.PlayerFactory;
import com.project.game.Scripts.*;
import com.project.physics.PhysicsBody.RigidBody2D;
import org.jetbrains.annotations.NotNull;

/**
 * A simple Level for Game PoC
 */
public class EasyLevelFactory {
    private static Scene createScene() {
        Engine e = Engine.getInstance();
        GameWindow w = e.getPrimaryWindow();

        if (w == null) {
            System.out.println("Failed to obtain primary window");
            e.exitEngine();
            return null;
        }

        while (!w.isReady()) {}

        //String serialized = FileIO.ReadText("tmp/serialized_scene.json");
        //w.setActiveScene(SerializeManager.deserialize(serialized));
        Scene s = new Scene("Test Scene");

        GameObject escapeDetector = new GameObject();
        escapeDetector.addBehavior(new SceneExit(LevelSelectionFactory::loadLevelSelection));
        s.addSceneObject(escapeDetector, true);

        GameObject o = getPlayerObject();
        s.addSceneObject(o, true);
        s.getCamera().update(o, 0);
        s.getCamera().setOffsetX(-100);


        s.addSceneObjects(getBackgroundObject(), getGroundObject(0), getGroundObject(1600),
                getFloatingObject1(), getFloatingObject2(),
                HiddenBlockFactory.generateHiddenBlock(1280, 380, false),
                HiddenBlockFactory.generateHiddenBlock(1280-64, 380, false),
                HiddenSpikeFactory.generateHiddenSpike(900, 600),
                getCloud(100, 50), getCloud(500, 100), getCloud(900, 90), getCloud(1400, 70));

        return s;
    }

    /**
     * Creates sample object with input script. WASD to move object, arrow keys to move camera.
     * @return The test object
     */
    private static @NotNull GameObject getPlayerObject() {
        return PlayerFactory.createPlayer();
    }


    private static @NotNull GameObject getBackgroundObject() {
        GameObject o = new GameObject();
        SpriteRenderer sr = new SpriteRenderer("assets/CSC207_asset_bg.png", 800,800);
        sr.setIndependentOfCamera(true);
        o.getTransform().setPosition(0,0);
        o.getTransform().setZIndex(0);
        o.addRenderable(sr);
        return o;
    }

    private static @NotNull GameObject getGroundObject(int xPos) {
        GameObject o = new GameObject();
        o.addTag("ground");
        SpriteRenderer sr = new SpriteRenderer("assets/ground_brick.png", 128,128);
        sr.setTile(true);
        sr.setTileX(10);

        o.getTransform().setPosition(xPos, 600);
        o.getTransform().setScaleX(10);

        o.addBehavior(new GroundStats(0.5));
        o.addBehavior(new SimpleCollider());
        o.getTransform().setZIndex(1);
        o.addRenderable(sr);
        return o;
    }

    private static @NotNull GameObject getFloatingObject1() {
        GameObject o = new GameObject();
        o.addTag("ground");
        SpriteRenderer sr = new SpriteRenderer("assets/brick.png", 128,128);

        o.getTransform().setPosition(500, 520);
        o.addBehavior(new GroundStats(0.5));
        o.addBehavior(new SimpleCollider());
        o.getTransform().setZIndex(0);
        o.addRenderable(sr);
        return o;
    }

    private static @NotNull GameObject getFloatingObject2() {
        GameObject o = new GameObject();
        o.addTag("ground");
        SpriteRenderer sr = new SpriteRenderer("assets/brick.png", 128,64);

        o.getTransform().setPosition(700, 400);
        o.addBehavior(new GroundStats(1));
        o.addBehavior(new SimpleCollider());
        o.getTransform().setZIndex(1);
        o.addRenderable(sr);
        return o;
    }

    public static @NotNull GameObject getCloud(int x, int y) {
        GameObject cloud = new GameObject("Cloud");
        SpriteRenderer cloudRenderer = new SpriteRenderer("assets/CSC207_asset_cloud.png", 800, 800);
        cloud.getTransform().setPosition(x, y);
        cloud.getTransform().setZIndex(1);
        cloud.getTransform().setScaleX(0.5);
        cloud.getTransform().setScaleY(0.2);
        cloud.addRenderable(cloudRenderer);
        return cloud;
    }


    public static void loadEasyLevel() {
        Engine.getInstance().getPrimaryWindow().setWindowSizeForce(800, 800);
        Scene s = createScene();
        Engine.getInstance().getPrimaryWindow().setActiveScene(s);
    }
}
