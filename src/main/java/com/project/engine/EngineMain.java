package com.project.engine;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.MovementController;
import com.project.engine.Scripting.WindowStatsDebug;
import com.project.engine.Serialization.SerializeManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class EngineMain {

    public static void main(String[] args) {
        Engine e = Engine.getInstance();
        GameWindow w = e.getPrimaryWindow();
        if (w == null) {
            System.out.println("Failed to obtain primary window");
            System.exit(1);
            return;
        }
        while (!w.isReady()) {}
        w.setWindowSize(800, 800);
        String serialized = "{\"scene_data\":{\"name\":\"Empty Scene\",\"scene_objects\":[{\"transform\":{\"rotation\":0,\"scale\":[1,1],\"position\":[336,300],\"zIndex\":1},\"renderables\":[{\"spritePath\":\"assets/penguin.jpeg\",\"width\":128,\"class\":\"com.project.engine.Rendering.SpriteRenderer\",\"height\":128}],\"is_listener\":true,\"scriptables\":[{\"canMove\":true,\"moveSpeed\":2,\"enableYMovement\":true,\"jumpForce\":10,\"class\":\"com.project.engine.Scripting.MovementController\"}],\"name\":\"GameObject\",\"tag\":\"Untagged\"}]}}\n";
        w.setActiveScene(SerializeManager.deserialize(serialized));

        JLabel label = new JLabel("_-_-_-Hello, World!-_-_-_");
        label.setForeground(Color.decode("#b500b5"));
        label.setFont(label.getFont().deriveFont(64.0f));
        // set background
        label.setOpaque(true);
        label.setBackground(Color.decode("#00ff00"));
        label.setBounds(0, 200, 800, 100); // Set bounds for absolute positioning
        // align center
        label.setHorizontalAlignment(SwingConstants.CENTER);
        w.addUIElement(label);

        //GameObject o = getTestObject();
        //w.getActiveScene().addSceneObject(o, true);
        //System.out.println(SerializeManager.serialize(w.getActiveScene()));

        // Remove this comment to run the stress test
        //measureAndRemoveObjects(w);
    }

    /**
     * Creates sample object with input script. WASD to move object, arrow keys to move camera.
     * @return The test object
     */
    private static @NotNull GameObject getTestObject() {
        GameObject o = new GameObject();
        SpriteRenderer sr = new SpriteRenderer("assets/penguin.jpeg", 128,128);
        o.getTransform().setPosition(336,300);
        o.getTransform().setZIndex(1);
        o.addBehavior(new MovementController());
        o.addBehavior(new WindowStatsDebug());
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
