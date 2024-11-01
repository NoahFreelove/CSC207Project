package com.project.engine;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.MovementController;
import com.project.engine.Scripting.WindowStatsDebug;
import org.jetbrains.annotations.NotNull;

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
        w.setWindowSize(2000, 2000);

        GameObject o = getTestObject();
        w.getActiveScene().addSceneObject(o, true);

        // do a lil stress test
        measureAndRemoveObjects(w);
    }

    /**
     * Creates sample object with input script. WASD to move object, arrow keys to move camera.
     * @return The test object
     */
    private static @NotNull GameObject getTestObject() {
        GameObject o = new GameObject();
        SpriteRenderer sr = new SpriteRenderer("assets/penguin.jpeg", 128,128);
        o.getTransform().setPosition(250,300);

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
        final int MEASUREMENT_DURATION_MS = 250;
        final int OBJECTS_TO_REMOVE = 50;

        final int STRESS_TEST_MAX = 1000;

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