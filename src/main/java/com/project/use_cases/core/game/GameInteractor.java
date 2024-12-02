package com.project.use_cases.core.game;

import com.project.entity.core.Scene;
import com.project.entity.core.Tuple;
import com.project.entity.input.EInputType;
import com.project.use_cases.player_move.PlayerMoveInputData;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;

public class GameInteractor {
    // Singleton design pattern 😎
    private volatile static GameInteractor instance = null;
    private boolean paused = false;

    private final ArrayList<GameOutputData> gameWindows = new ArrayList<>();
    private PlayerMoveInputData inputCache = new PlayerMoveInputData();

    public static String DEFAULT_NEW_WINDOW_NAME = "Game Window";

    public Runnable closeHook = () -> {};

    private GameInteractor(){
        instance = this;
        init();
    }

    /**
     * Get the instance of the engine (singleton).
     * @return The instance of the engine
     */
    public static GameInteractor getInstance(){
        if (instance == null) {
            return new GameInteractor();
        }
        return instance;
    }

    public static Tuple<GameInteractor, GameOutputData> createAndWait() {
        if (instance == null)
        {
            GameInteractor outEngine = getInstance();
            GameOutputData outWindow = outEngine.getPrimaryWindow();
            while (!outWindow.isReady()) {}
            return new Tuple<>(outEngine, outWindow);
        }
        while (!instance.getPrimaryWindow().isReady()) {}
        return new Tuple<>(instance, instance.getPrimaryWindow());
    }


    public GameOutputData getPrimaryWindow(){
        if(gameWindows.isEmpty()){
            System.err.println("Could not fetch primary window, exiting...");
            System.exit(1);
            return GameOutputData.nullWindow();
        }
        return gameWindows.get(0);
    }

    @Nullable
    public GameOutputData getWindowByName(String name){
        for (GameOutputData window : gameWindows){
            if (window.getName().equals(name)){
                return window;
            }
        }
        return null;
    }

    private void init(){
        this.gameWindows.add(GameOutputData.createGameWindow(800, 800, DEFAULT_NEW_WINDOW_NAME, new GameLoop()));
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
        for (GameOutputData gameWindow : gameWindows) {
            gameWindow.closeWindow();
        }
        closeHook.run();
    }

    public void pauseGame() {
        inputCache.resetInput();

        paused = true;
        runPauseEvents(true);
    }

    public void unpauseGame() {
        paused = false;
        runPauseEvents(false);
    }

    public boolean isPaused() {
        return paused;
    }

    private void runPauseEvents(boolean paused){
        for (GameOutputData w : gameWindows)
            w.getActiveScene().pauseEvent(paused);
    }

    public PlayerMoveInputData getInputCache() {
        return inputCache;
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
