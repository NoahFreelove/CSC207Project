package com.project.engine.Core;

import com.project.engine.Input.EInputType;
import com.project.engine.Rendering.Camera;
import com.project.engine.Rendering.IRenderable;
import com.project.engine.Scripting.IScriptable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Scene {

    private final String name;

    private final ArrayList<GameObject> sceneObjects = new ArrayList<>();

    private final ArrayList<Tuple<GameObject, IScriptable>> inputListeners = new ArrayList<>();

    /**
     * Why not just loop through every game object and get the renderables?
     * Because in our specific game - we plan to have many invisible triggers and other objects that don't render.
     * This way, we can have a list of renderables that is only the objects that actually render.
     */
    private final ArrayList<Tuple<GameObject, IRenderable>> renderables = new ArrayList<>();

    private Camera camera = new Camera();


    public Scene() {
        this.name = "Empty Scene";
    }

    /**
     * This method exists to allow you to add listeners without checking the whole scene if the parent object is in
     * the scene. This is useful for when you are batch adding listeners, and it would be inefficient to check if the
     * parent object is in the scene each time.
     * @param listenerScript The listener to add
     */
    private void addInputListenerCheckless(GameObject ref, IScriptable listenerScript){
        Tuple<GameObject, IScriptable> listener = new Tuple<>(ref, listenerScript);

        if (inputListeners.contains(listener))
            return;
        inputListeners.add(listener);
    }

    public void addInputListener(GameObject ref, IScriptable listenerScript){
        if(!sceneObjects.contains(ref)) {
            System.err.println("Object <" + ref.toString() + "> not in scene - cannot add listener");
            return;
        }

        if (!ref.hasScript(listenerScript)){
            System.err.println("Object <" + ref + "> does not have script <" + listenerScript.toString()
                    + "> - cannot add listener");
            return;
        }

        addInputListenerCheckless(ref, listenerScript);
    }


    private void addRenderableCheckless(GameObject ref, IRenderable renderable){
        Tuple<GameObject, IRenderable> renderableTuple = new Tuple<>(ref, renderable);
        if (renderables.contains(renderableTuple))
            return;
        renderables.add(renderableTuple);
    }

    public void addRenderable(GameObject ref, IRenderable renderable){
        if (!sceneObjects.contains(ref)){
            System.err.println("Object <" + ref.toString() + "> not in scene - cannot add renderable");
            return;
        }
        addRenderableCheckless(ref, renderable);
    }

    public void addAllInputListeners(GameObject object){
        if (!sceneObjects.contains(object)){
            System.err.println("Object <" + object.toString() + "> not in scene - cannot add listeners");
            return;
        }

        Iterator<IScriptable> scripts = object.getBehaviors();
        while (scripts.hasNext()){
            IScriptable script = scripts.next();
            addInputListenerCheckless(object, script);
        }
    }

    public void addAllRenderables(GameObject object){
        if (!sceneObjects.contains(object)){
            System.err.println("Object <" + object.toString() + "> not in scene - cannot add renderables");
            return;
        }
        Iterator<IRenderable> renderables = object.getRenderables();
        while (renderables.hasNext()){
            IRenderable renderable = renderables.next();
            addRenderableCheckless(object, renderable);
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
        // find the listener
        Tuple<GameObject, IScriptable> listener = null;
        for (Tuple<GameObject, IScriptable> tuple : inputListeners){
            if (tuple.getSecond().equals(listenerScript)){
                listener = tuple;
                break;
            }
        }
        inputListeners.remove(listener);
    }

    public void removeRenderable(IRenderable renderable) {
        Tuple<GameObject, IRenderable> renderableTuple = null;
        for (Tuple<GameObject, IRenderable> tuple : renderables) {
            if (tuple.getSecond().equals(renderable)) {
                renderableTuple = tuple;
                break;
            }
        }
        renderables.remove(renderableTuple);
    }

    public String getName() {
        return name;
    }

    public void onInput(String keyName, EInputType inputType, int inputMods) {
        for (Tuple<GameObject, IScriptable> listener : inputListeners) {
            listener.getSecond().onInput(listener.getFirst(), keyName, inputType, inputMods);
        }
    }

    public Iterator<GameObject> getSceneObjects() {
        return sceneObjects.iterator();
    }

    /**
     * Add a GameObject to the scene.
     * Important! Input listeners are not automatically added, but renderers are.
     * If you add a renderer post-scene addition, you must add it manually.
     * @param object the object to add
     * @return true if the object was added, false if it was already present (or if it failed to add in some way)
     */
    public boolean addSceneObject(GameObject object) {
        if (sceneObjects.contains(object))
            return false;

        if(!sceneObjects.add(object))
            return false;

        addAllRenderables(object);
        return true;
    }

    /**
     * Add a GameObject to the scene. If addAllAsListeners is true, all scripts on the object will be added
     * and interpreted as input listeners. This is useful if you're lazy and don't want to add them manually.
     * @param object the object to add
     * @param addAllAsListeners whether to add all scripts as input listeners
     * @return true if the object was added, false if it was already present.
     */
    public boolean addSceneObject(GameObject object, boolean addAllAsListeners) {
        boolean result = addSceneObject(object);
        if(!addAllAsListeners) {
            return result;
        }

        addAllInputListeners(object);
        return result;
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

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    // region Scripting Methods
    public void start(){
        for (GameObject object : sceneObjects){
            Iterator<IScriptable> scripts = object.getBehaviors();
            while (scripts.hasNext()){
                IScriptable script = scripts.next();
                script.start(object);
            }
        }
    }

    public void update(double deltaTime){
        for (GameObject object : sceneObjects){
            Iterator<IScriptable> scripts = object.getBehaviors();
            while (scripts.hasNext()){
                IScriptable script = scripts.next();
                script.update(object, deltaTime);
            }
        }
    }

    public void stop(){
        for (GameObject object : sceneObjects){
            Iterator<IScriptable> scripts = object.getBehaviors();
            while (scripts.hasNext()){
                IScriptable script = scripts.next();
                script.stop(object);
            }
        }
    }

    public void render(JPanel root){
        for (Tuple<GameObject, IRenderable> renderable : renderables){
            JComponent comp = renderable.getSecond().renderComponent(renderable.getFirst(), this);
            root.add(comp);
        }
    }
    // endregion


    // region Static Methods

    public static Scene NullScene() {
        return new Scene();
    }

    // endregion
}
