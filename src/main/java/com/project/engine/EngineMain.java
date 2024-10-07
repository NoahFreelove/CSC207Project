package com.project.engine;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.MovementController;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class EngineMain {
    public static void main(String[] args) {
        Engine e = Engine.getInstance();
        GameWindow w = e.getPrimaryWindow();
        if (w == null){
            System.out.println("Failed to obtain primary window");
            System.exit(1);
            return;
        }

        GameObject o = getTestObjet();
        w.getActiveScene().addSceneObject(o, true);

        Scanner s = new Scanner(System.in);
        while (true){
            System.out.println("Enter 'q' to quit");
            if (s.nextLine().equals("q")){
                w.closeWindow();
                break;
            }
        }

    }

    /**
     * Creates sample object with input script. WASD to move object, arrow keys to move camera.
     * @return The test object
     */
    private static @NotNull GameObject getTestObjet() {
        GameObject o = new GameObject();
        SpriteRenderer sr = new SpriteRenderer("assets/penguin.jpeg");

        o.addBehavior(new MovementController());
        o.addRenderable(sr);
        return o;
    }
}