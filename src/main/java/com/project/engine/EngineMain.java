package com.project.engine;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.GameWindow;
import com.project.engine.Input.EInputType;
import com.project.engine.Input.InputMods;
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

    private static @NotNull GameObject getTestObjet() {
        GameObject o = new GameObject();

        IScriptable simpleInputScript = new IScriptable() {
            @Override
            public void onInput(GameObject ref, String keyName, EInputType inputType, int mods) {
                if (inputType == EInputType.TYPED){
                    System.out.println("Input: " + keyName + " " + inputType);
                    System.out.println("Is Shift? " + InputMods.isShift(mods));
                    System.out.println(ref.toString());
                }
            }
        };
        o.addBehavior(simpleInputScript);
        o.addRenderable(new SpriteRenderer("assets/penguin.jpeg"));
        return o;
    }
}