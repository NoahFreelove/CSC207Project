package com.project.engine;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.GameWindow;

import java.util.Scanner;

public class EngineMain {
    public static void main(String[] args) {
        System.out.println(new GameObject().getUid());
        System.out.println(new GameObject().getUid());
        Engine e = Engine.getInstance();
        GameWindow w = e.getPrimaryWindow();

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