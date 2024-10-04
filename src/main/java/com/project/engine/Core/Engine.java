package com.project.engine.Core;

public class Engine {
    // Singleton design pattern 😎
    private static Engine instance = null;

    private Scene activeScene = Scene.NullScene();

    private Engine(){
        instance = this;
        init();

    }

    public Engine getInstance(){
        if (instance == null) {
            return new Engine();
        }
        return instance;
    }

    private void init(){

    }
}
