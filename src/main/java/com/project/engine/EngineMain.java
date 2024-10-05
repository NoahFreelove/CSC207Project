package com.project.engine;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.GameWindow;
import com.project.engine.Input.EInputType;
import com.project.engine.Input.InputMods;
import com.project.engine.Rendering.Camera;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.IScriptable;
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
                w.close();
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

        IScriptable simpleInputScript = new IScriptable() {
            float speed = 300;
            final GameWindow w = Engine.getInstance().getPrimaryWindow();
            Camera c = w.getActiveScene().getCamera();


            @Override
            public void update(GameObject ref, double delta) {
                if (w == null)
                    return;

                if(w.isKeyPressed("w")){
                    ref.translate(0, -1*speed*delta);
                }
                else if(w.isKeyPressed("s")){
                    ref.translate(0, 1*speed*delta);
                }
                if(w.isKeyPressed("a")){
                    ref.translate(-1*speed*delta, 0);
                }
                else if(w.isKeyPressed("d")){
                    ref.translate(1*speed*delta, 0);
                }

                if(w.isKeyPressed("up")){
                    c.translate(0, -1*speed*delta);
                }
                else if(w.isKeyPressed("down")){
                    c.translate(0, 1*speed*delta);
                }
                if(w.isKeyPressed("left")){
                    c.translate(-1*speed*delta, 0);
                }
                else if(w.isKeyPressed("right")){
                    c.translate(1*speed*delta, 0);
                }
            }
        };
        o.addBehavior(simpleInputScript);
        o.addRenderable(sr);
        return o;
    }
}