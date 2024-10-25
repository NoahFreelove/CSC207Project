package com.project.engine.Core;

import com.project.engine.Input.EInputType;
import com.project.engine.Core.Window.GameWindow;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
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
        this.gameWindows.add(GameWindow.createGameWindow(800, 800, "Game Window"));
    }

    /**
     * Render scene to a child panel of the root panel.
     * @param root MUST have exactly one child that is a JPanel. This will be swapped out for the new render.
     * @param scene the scene to render
     */
    public void render(JPanel root, Scene scene){
        // oldRenderPass(root, scene);
        newRenderPass(root,scene);
    }


    private static void oldRenderPass(JPanel root, Scene scene) {
        JPanel frame = (JPanel) root.getComponent(0);
        frame.removeAll();
        scene.render(frame);
    }

    private static void newRenderPass(JPanel root, Scene scene) {
        JPanel renderPanel = new JPanel();
        renderPanel.setLayout(null);
        renderPanel.setBounds(0,0, root.getBounds().width, root.getBounds().height);
        // get the one child of the root
        JPanel tmp = (JPanel) root.getComponent(0);

        scene.render(renderPanel);
        // replace the root with the new root
        root.add(renderPanel);
        // seamless transition
        root.remove(tmp);
    }

    public void update(Scene scene, double delta){
        scene.update(delta);
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
