package com.project.game.LevelPresets;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.*;
import com.project.physics.Collision.CollisionVolume;
import com.project.physics.PhysicsBody.RigidBody2D;
import org.jetbrains.annotations.NotNull;

/**
 * A simple Level for Game PoC
 */
public class EasyLevel {
    public static void main(String[] args) {
        loadTestScene();
    }

    public static void loadTestScene() {
        Engine e = Engine.getInstance();
        GameWindow w = e.getPrimaryWindow();

        if (w == null) {
            System.out.println("Failed to obtain primary window");
            System.exit(1);
            return;
        }

        while (!w.isReady()) {}

        //String serialized = FileIO.ReadText("tmp/serialized_scene.json");
        //w.setActiveScene(SerializeManager.deserialize(serialized));
        Scene s = new Scene("Test Scene");

        GameObject o = getPlayerObject();
        s.addSceneObject(o, true);
        s.getCamera().update(o, 0);
        s.getCamera().setOffsetX(-100);

        o = getBackgroundObject();
        s.addSceneObject(o, true);

        for (int i = 0; i < 10; i++) {
            o = getGroundObject(false, i * 128, 600);

            if (i == 0) {
                o.addBehavior(new GroundStats(0.5));
                o.addBehavior(new SimpleCollider(0, 0, 10, 1));
            }
            s.addSceneObject(o, true);
        }

        w.setActiveScene(s);
    }

    /**
     * Creates sample object with input script. WASD to move object, arrow keys to move camera.
     * @return The test object
     */
    private static @NotNull GameObject getPlayerObject() {
        GameObject o = new GameObject();
        o.setTag("player");
        SpriteRenderer sr = new SpriteRenderer("assets/character_right", 128,128);
        sr.setIndependentOfCamera(false);
        o.getTransform().setPosition(336,300);
        o.getTransform().setZIndex(2);
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

    private static @NotNull GameObject getGroundObject(boolean fake, double x, double y) {
        GameObject o = new GameObject();
        o.setTag("ground");
        SpriteRenderer sr = new SpriteRenderer("assets/CSC207_asset_brick.png", 128,128);
        o.getTransform().setPosition(x,y);
        o.getTransform().setZIndex(1);
        o.addRenderable(sr);
        return o;
    }
}
