package com.project.game.Scenes;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.WindowStatsDebug;
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

        o = getBackgroundObject();
        s.addSceneObject(o, true);
        o = getGroundObject();
        s.addSceneObject(o, true);
        o = getFloatingObject1();
        s.addSceneObject(o, true);
        o = getFloatingObject2();
        s.addSceneObject(o, true);

        o = getCloud(100, 50);
        s.addSceneObject(o, true);
        o = getCloud(500, 100);
        s.addSceneObject(o, true);
        o = getCloud(900, 90);
        s.addSceneObject(o, true);
        o = getCloud(1400, 70);
        s.addSceneObject(o, true);

        return s;
    }

    /**
     * Creates sample object with input script. WASD to move object, arrow keys to move camera.
     * @return The test object
     */
    private static @NotNull GameObject getPlayerObject() {
        GameObject o = new GameObject();
        o.setTag("player");
        SpriteRenderer sr = new SpriteRenderer("assets/character.png", 128,128);
        sr.setIndependentOfCamera(false);
        o.getTransform().setPosition(0,0);
        o.getTransform().setZIndex(2);
        o.addBehavior(new SpawnPoint(300, 480));
        o.addBehavior(new DeathJoke());
        o.addBehavior(new MovementController());
        o.addBehavior(new RigidBody2D());
        o.addBehavior(new SimpleCollider(32, 19.2, 0.5, 0.7));
        o.addBehavior(new GroundTrigger(32, 99, 0.5, 0.1));
        o.addBehavior(new WindowStatsDebug());
        o.addRenderable(sr);
        return o;
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

    private static @NotNull GameObject getGroundObject() {
        GameObject o = new GameObject();
        o.setTag("ground");
        SpriteRenderer sr = new SpriteRenderer("assets/ground_brick.png", 128,128);
        sr.setTile(true);
        sr.setTileX(10);

        o.getTransform().setPosition(0, 600);
        o.getTransform().setScaleX(10);

        o.addBehavior(new GroundStats(0.5));
        o.addBehavior(new SimpleCollider());
        o.getTransform().setZIndex(1);
        o.addRenderable(sr);
        return o;
    }

    private static @NotNull GameObject getFloatingObject1() {
        GameObject o = new GameObject();
        o.setTag("ground");
        SpriteRenderer sr = new SpriteRenderer("assets/CSC207_asset_brick.png", 128,128);

        o.getTransform().setPosition(500, 520);
        o.addBehavior(new GroundStats(0.5));
        o.addBehavior(new SimpleCollider());
        o.getTransform().setZIndex(0);
        o.addRenderable(sr);
        return o;
    }

    private static @NotNull GameObject getFloatingObject2() {
        GameObject o = new GameObject();
        o.setTag("ground");
        SpriteRenderer sr = new SpriteRenderer("assets/CSC207_asset_brick.png", 128,64);

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
