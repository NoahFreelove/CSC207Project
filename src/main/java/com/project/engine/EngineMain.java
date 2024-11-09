package com.project.engine;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.*;
import com.project.engine.UI.GameUILabel;
import com.project.engine.UI.GameUIPanel;
import com.project.physics.PhysicsBody.RigidBody2D;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class EngineMain {

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

        GameUIPanel panel = new GameUIPanel(350,750, 50, 50);

        panel.setBackground(Color.ORANGE);

        s.addUIElement(panel);

        GameUILabel label1 = new GameUILabel("Hello, World!", 0, 200, 800, 100);
        label1.setForeground(Color.decode("#b500b5"));
        label1.setFont(label1.getFont().deriveFont(64.0f));
        label1.setBackground(Color.decode("#00ff00"));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        s.addUIElement(label1);


        GameObject o = getTestObject();
        s.addSceneObject(o, true);

        GameObject o2 = getStaticObject();
        s.addSceneObject(o2, true);

        GameObject o3 = getStaticObject2();
        s.addSceneObject(o3, true);

        w.setActiveScene(s);

        // FileIO.WriteText("/tmp/serialized_scene.json", SerializeManager.serialize(w.getActiveScene()).toString(4));
        // Remove this comment to run the stress test
        //measureAndRemoveObjects(w);
    }

    /**
     * Creates sample object with input script. WASD to move object, arrow keys to move camera.
     * @return The test object
     */
    private static @NotNull GameObject getTestObject() {
        GameObject o = new GameObject();
        SpriteRenderer sr = new SpriteRenderer("assets/CSC207_asset_char_right.png", 128,128);
        o.getTransform().setPosition(336,300);
        o.getTransform().setZIndex(1);
        o.addBehavior(new MovementController());
        o.addBehavior(new RigidBody2D());
        o.addBehavior(new SimpleCollider(0, 0, 1, 1));
        o.addBehavior(new GroundTrigger(6.4, 115.3, 0.9, 0.1));
        o.addBehavior(new WindowStatsDebug());
        o.addRenderable(sr);
        return o;
    }

    private static @NotNull GameObject getStaticObject() {
        GameObject o = new GameObject();
        SpriteRenderer sr = new SpriteRenderer("assets/CSC207_asset_ground_brick.png", 128,128);
        o.getTransform().setPosition(450,550);
        o.getTransform().setScaleX(2);
        o.getTransform().setZIndex(1);
        o.addBehavior(new SimpleTrigger());
        o.addRenderable(sr);
        return o;
    }

    private static @NotNull GameObject getStaticObject2() {
        GameObject o = new GameObject();
        o.setTag("ground");
        SpriteRenderer sr = new SpriteRenderer("assets/CSC207_asset_ground_brick.png", 128,128);
        o.getTransform().setPosition(200,550);
        o.getTransform().setScaleX(2);
        o.getTransform().setZIndex(1);
        o.addBehavior(new SimpleCollider());
        o.addBehavior(new GroundStats(0.5));
        o.addRenderable(sr);
        return o;
    }

    private static GameObject getDummyObject(double x, double y) {
        GameObject o = new GameObject();
        SpriteRenderer sr = new SpriteRenderer("assets/penguin.jpeg", 64, 64);
        o.getTransform().setPosition(x, y);
        o.addRenderable(sr);
        return o;
    }

    private static void measureAndRemoveObjects(GameWindow w) {
        final int MEASUREMENT_DURATION_MS = 1000;
        final int OBJECTS_TO_REMOVE = 250;

        final int STRESS_TEST_MAX = 2000;

        final long timeBeforeSetup = System.currentTimeMillis();
        System.out.print("Setting up stress test... ");

        for (int i = 0; i < STRESS_TEST_MAX; i++) {
            float x = (float) (Math.random() * 800 - 64);
            float y = (float) (Math.random() * 800 - 64);
            w.getActiveScene().addSceneObject(getDummyObject(x, y));
        }
        final long timeStartupDelta = System.currentTimeMillis() - timeBeforeSetup;
        System.out.print("(" + timeStartupDelta + "ms)\n");

        System.out.println("Starting stress test: (" + STRESS_TEST_MAX + " objects, " + OBJECTS_TO_REMOVE + " removed per iteration, " + MEASUREMENT_DURATION_MS + "ms per measurement)");
        while (w.getActiveScene().getSceneObjectCount() > 1) {
            long startTime = System.currentTimeMillis();
            long endTime = startTime + MEASUREMENT_DURATION_MS;
            double averageFPS = 0;

            while (System.currentTimeMillis() < endTime) {
                averageFPS = Engine.getInstance().getPrimaryWindow().FPS();
            }

            int objectCount = w.getActiveScene().getSceneObjectCount();
            System.out.println("Average FPS: " + averageFPS + " with " + objectCount + " objects");

            for (int i = 0; i < OBJECTS_TO_REMOVE && w.getActiveScene().getSceneObjectCount() > 0; i++) {
                w.getActiveScene().removeSceneObject(w.getActiveScene().getSceneObjectCount()-1);
            }
        }
    }
}
