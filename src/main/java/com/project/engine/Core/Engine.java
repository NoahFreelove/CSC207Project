package com.project.engine.Core;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class Engine {
    // Singleton design pattern 😎
    private volatile static Engine instance = null;

    private final ArrayList<GameWindow> gameWindows = new ArrayList<>();

    private Engine(){
        instance = this;
        init();
    }

    /**
     * Get the instance of the engine (singleton).
     * @return The instance of the engine
     */
    public static Engine getInstance(){
        if (instance == null) {
            return new Engine();
        }
        return instance;
    }


    @Nullable
    public GameWindow getPrimaryWindow(){
        if(gameWindows.isEmpty()){
            return null;
        }
        return gameWindows.get(0);
    }

    @Nullable
    public GameWindow getWindowByName(String name){
        for (GameWindow window : gameWindows){
            if (window.getName().equals(name)){
                return window;
            }
        }
        return null;
    }

    private void init(){
        this.gameWindows.add(GameWindow.createGameWindow(800, 600, "Game Window"));
    }

    public void render(Scene scene){

    }

    public void update(Scene scene, double delta){
        System.out.println("Updating Scene: " + scene.getName() + " Delta: " + delta);
    }
}
