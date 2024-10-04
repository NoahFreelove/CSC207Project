package com.project.engine.Core;

import com.project.engine.Input.EInputType;
import com.project.engine.Rendering.IRenderable;
import com.project.engine.Scripting.IScriptable;

import java.util.ArrayList;
import java.util.Iterator;

public class Scene {

    private final String name;

    private final ArrayList<GameObject> sceneObjects = new ArrayList<>();

    private final ArrayList<IScriptable> inputListeners = new ArrayList<>();

    private final ArrayList<IRenderable> renderables = new ArrayList<>();


    public Scene() {
        this.name = "Empty Scene";
    }

    public void addInputListener(IScriptable listenerScript){
        if (inputListeners.contains(listenerScript))
            return;
        inputListeners.add(listenerScript);
    }

    public void addRenderable(IRenderable renderable){
        if (renderables.contains(renderable))
            return;
        renderables.add(renderable);
    }

    public void addAllInputListeners(GameObject object){
        Iterator<IScriptable> scripts = object.getBehaviors();
        while (scripts.hasNext()){
            IScriptable script = scripts.next();
            addInputListener(script);
        }
    }

    public void addAllRenderables(GameObject object){
        Iterator<IRenderable> renderables = object.getRenderables();
        while (renderables.hasNext()){
            IRenderable renderable = renderables.next();
            addRenderable(renderable);
        }
    }

    public void removeAllInputListeners(GameObject object){
        Iterator<IScriptable> scripts = object.getBehaviors();
        while (scripts.hasNext()){
            IScriptable script = scripts.next();
            removeInputListener(script);
        }
    }

    public void removeAllRenderables(GameObject object){
        Iterator<IRenderable> renderables = object.getRenderables();
        while (renderables.hasNext()){
            IRenderable renderable = renderables.next();
            removeRenderable(renderable);
        }
    }

    public void removeInputListener(IScriptable listenerScript){
        inputListeners.remove(listenerScript);
    }

    public void removeRenderable(IRenderable renderable){
        renderables.remove(renderable);
    }

    public String getName() {
        return name;
    }

    public void onInput(String keyName, EInputType inputType){
        for (IScriptable listener : inputListeners){
            listener.onInput(keyName, inputType);
        }
    }

    public Iterator<GameObject> getSceneObjects() {
        return sceneObjects.iterator();
    }

    public boolean addSceneObject(GameObject object) {
        if (sceneObjects.contains(object))
            return false;
        return sceneObjects.add(object);
    }

    public boolean removeSceneObject(GameObject object) {
        removeAllInputListeners(object);
        removeAllRenderables(object);

        return sceneObjects.remove(object);
    }

    public boolean removeSceneObject(GameObject object, boolean dontDestroyListeners){
        if (!dontDestroyListeners) {
            removeAllInputListeners(object);
        }
        removeAllRenderables(object);
        return sceneObjects.remove(object);
    }

    // region Scripting Methods
    public void start(){
        for (GameObject object : sceneObjects){
            Iterator<IScriptable> scripts = object.getBehaviors();
            while (scripts.hasNext()){
                IScriptable script = scripts.next();
                script.start();
            }
        }
    }

    public void update(double deltaTime){
        for (GameObject object : sceneObjects){
            Iterator<IScriptable> scripts = object.getBehaviors();
            while (scripts.hasNext()){
                IScriptable script = scripts.next();
                script.update(deltaTime);
            }
        }
    }

    public void stop(){
        for (GameObject object : sceneObjects){
            Iterator<IScriptable> scripts = object.getBehaviors();
            while (scripts.hasNext()){
                IScriptable script = scripts.next();
                script.stop();
            }
        }
    }

    public void render(){
        for (IRenderable renderable : renderables){
            renderable.renderComponent();
        }
    }


    // region Static Methods

    public static Scene NullScene() {
        return new Scene();
    }

    // endregion
}
