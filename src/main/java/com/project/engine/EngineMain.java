package com.project.engine;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.GameWindow;
import com.project.engine.Input.EInputType;
import com.project.engine.Scripting.IScriptable;

import java.util.Scanner;

public class EngineMain {
    public static void main(String[] args) {
        Engine e = Engine.getInstance();
        GameWindow w = e.getPrimaryWindow();
        GameObject o = new GameObject();

        IScriptable simpleInputScript = new IScriptable() {
            @Override
            public void onInput(String keyName, EInputType inputType) {
                System.out.println("Input: " + keyName + " " + inputType);
            }
        };
        o.addBehavior(simpleInputScript);

        w.getActiveScene().addSceneObject(o);
        w.getActiveScene().addInputListener(simpleInputScript);

        Scanner s = new Scanner(System.in);
        while (true){
            System.out.println("Enter 'q' to quit");
            if (s.nextLine().equals("q")){
                if (w != null) {
                    w.close();
                    break;
                }
            }
        }

    }
}