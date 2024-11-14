package com.project.engine.Core;

import com.project.engine.Input.EInputType;
import com.project.engine.Core.Window.GameWindow;
import com.project.game.tts.PlayTTS;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Engine {
    // Singleton design pattern 😎
    private volatile static Engine instance = null;
    private boolean paused = false;

    private final ArrayList<GameWindow> gameWindows = new ArrayList<>();

    public Runnable closeHook = () -> {};

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

    public static Tuple<Engine, GameWindow> createAndWait() {
        if (instance == null)
        {
            Engine outEngine = getInstance();
            GameWindow outWindow = outEngine.getPrimaryWindow();
            while (!outWindow.isReady()) {}
            return new Tuple<>(outEngine, outWindow);
        }
        while (!instance.getPrimaryWindow().isReady()) {}
        return new Tuple<>(instance, instance.getPrimaryWindow());
    }


    @Nullable
    public GameWindow getPrimaryWindow(){
        if(gameWindows.isEmpty()){
            System.err.println("Could not fetch primary window, exiting...");
            System.exit(1);
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
        this.gameWindows.add(GameWindow.createGameWindow(800, 800, "Game Window"));
    }

    /**
     * Render scene to a child panel of the root panel.
     * @param scene the scene to render
     * @param g2d the graphics object to render with
     */

    public void render(Scene scene, Graphics2D g2d) {
        scene.render(g2d);
    }

    public void update(Scene scene, double delta){
        if (paused)
            return;
        scene.update(delta);
    }

    public void physicsUpdate(Scene scene){
        if (paused)
            return;
        scene.physicsUpdate();
    }

    public void start(Scene scene){
        scene.start();
    }

    /**
     * Called right before next scene is loaded.
     * @param scene the scene that is being stopped
     */
    public void stopScene(Scene scene){
        scene.stop();
    }

    public void exitEngine() {
        for (GameWindow gameWindow : gameWindows) {
            gameWindow.closeWindow();
        }
        closeHook.run();
    }

    public void pauseGame() {
        paused = true;
    }

    public void unpauseGame() {
        paused = false;
    }

    public boolean isPaused() {
        return paused;
    }

    /**
     * Input method for the engine.
     * @param scene the scene that the input is coming from
     * @param keyName the name of the key that was pressed
     * @param inputType the type of input that was received
     */
    public void input(Scene scene, String keyName, EInputType inputType, int inputMods){
        scene.onInput(keyName.toUpperCase(), inputType, inputMods);
    }
}
