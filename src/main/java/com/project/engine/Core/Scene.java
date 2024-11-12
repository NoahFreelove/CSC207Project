package com.project.engine.Core;

import com.project.engine.Core.Window.WindowUICallback;
import com.project.engine.Input.EInputType;
import com.project.engine.Rendering.Camera;
import com.project.engine.Rendering.IRenderable;
import com.project.engine.Scripting.IScriptable;
import com.project.engine.Serialization.ISerializable;
import com.project.physics.Collision.CollisionManager;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class Scene implements ISerializable {

    private final String name;
    private final CollisionManager collisionManager = new CollisionManager();
    private final CopyOnWriteArrayList<GameObject> sceneObjects = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<JComponent> uiElements = new CopyOnWriteArrayList<>();

    private final CopyOnWriteArrayList<Tuple<GameObject, IScriptable>> inputListeners = new CopyOnWriteArrayList<>();

    private ArrayList<WindowUICallback> uiCallbacks = new ArrayList<>();
    /**
     * Why not just loop through every game object and get the renderables?
     * Because in our specific game - we plan to have many invisible triggers and other objects that don't render.
     * This way, we can have a list of renderables that is only the objects that actually render.
     */
    private final CopyOnWriteArrayList<Tuple<GameObject, IRenderable>> renderables = new CopyOnWriteArrayList<>();

    private Camera camera = new Camera();

    private double scaleX = 1.0;
    private double scaleY = 1.0;


    public Scene() {
        this.name = "Empty Scene";
    }

    public Scene(String name) {
        this.name = name;
    }

    /**
     * This method exists to allow you to add listeners without checking the whole scene if the parent object is in
     * the scene. This is useful for when you are batch adding listeners, and it would be inefficient to check if the
     * parent object is in the scene each time.
     * @param listenerScript The listener to add
     */
    private synchronized void addInputListenerCheckless(GameObject ref, IScriptable listenerScript){
        Tuple<GameObject, IScriptable> listener = new Tuple<>(ref, listenerScript);

        if (inputListeners.contains(listener))
            return;
        inputListeners.add(listener);
    }

    public synchronized void addInputListener(GameObject ref, IScriptable listenerScript){
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


    private synchronized void addRenderableCheckless(GameObject ref, IRenderable renderable){
        Tuple<GameObject, IRenderable> renderableTuple = new Tuple<>(ref, renderable);
        if (renderables.contains(renderableTuple))
            return;
        renderables.add(renderableTuple);

        sortRenderablesByZIndex();
    }

    private void sortRenderablesByZIndex() {
        renderables.sort((o1, o2) -> {
            float z1 = o1.getFirst().getTransform().getZIndex();
            float z2 = o2.getFirst().getTransform().getZIndex();
            return Float.compare(z1, z2);
        });
    }

    public synchronized void addRenderable(GameObject ref, IRenderable renderable){
        if (!sceneObjects.contains(ref)){
            System.err.println("Object <" + ref.toString() + "> not in scene - cannot add renderable");
            return;
        }
        addRenderableCheckless(ref, renderable);
    }

    public synchronized void addAllInputListeners(GameObject object){
        if (!sceneObjects.contains(object)){
            System.err.println("Object <" + object.toString() + "> not in scene - cannot add listeners");
            return;
        }

        Iterator<IScriptable> scripts = object.getScriptables();
        while (scripts.hasNext()){
            IScriptable script = scripts.next();
            addInputListenerCheckless(object, script);
        }
    }

    public synchronized void addUIElement(JComponent element) {
        addUIElement(element, false);
    }

    public synchronized void addUIElement(JComponent element, boolean persistent) {
        if (uiElements.contains(element))
            return;
        if(!persistent)
            uiElements.add(element);
        uiCallbacks.forEach(windowUICallback -> windowUICallback.addUIComponent(element));
    }

    public synchronized void addUICallback(WindowUICallback callback) {
        if (uiCallbacks.contains(callback))
            return;
        uiCallbacks.add(callback);
        uiElements.forEach(callback::addUIComponent);
    }

    public synchronized void removeUIElement(JComponent element) {
        uiElements.remove(element);
        uiCallbacks.forEach(windowUICallback -> windowUICallback.removeUIComponent(element));
    }

    public synchronized void removeUICallback(WindowUICallback callback) {
        uiCallbacks.remove(callback);
        uiElements.forEach(callback::removeUIComponent);
    }

    public synchronized CopyOnWriteArrayList<JComponent> getUIElements() {
        return uiElements;
    }


    public synchronized void addAllRenderables(GameObject object){
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

    public synchronized void removeAllInputListeners(GameObject object){
        Iterator<IScriptable> scripts = object.getScriptables();
        while (scripts.hasNext()){
            IScriptable script = scripts.next();
            removeInputListener(script);
        }
    }

    public synchronized void removeAllRenderables(GameObject object){
        Iterator<IRenderable> renderables = object.getRenderables();
        while (renderables.hasNext()){
            IRenderable renderable = renderables.next();
            removeRenderable(renderable);
        }
    }

    public synchronized void removeInputListener(IScriptable listenerScript){
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

    public synchronized void removeRenderable(IRenderable renderable) {
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
    public synchronized boolean addSceneObject(GameObject object) {
        if (sceneObjects.contains(object))
            return false;

        if(!sceneObjects.add(object))
            return false;
        object.linkTo(this);
        addAllRenderables(object);
        return true;
    }

    public synchronized boolean addSceneObjects(GameObject... object) {
        boolean result = true;
        for(GameObject o : object) {
            result &= addSceneObject(o);
        }
        return result;
    }

    /**
     * Add a GameObject to the scene. If addAllAsListeners is true, all scripts on the object will be added
     * and interpreted as input listeners. This is useful if you're lazy and don't want to add them manually.
     * @param object the object to add
     * @param addAllAsListeners whether to add all scripts as input listeners
     * @return true if the object was added, false if it was already present.
     */
    public synchronized boolean addSceneObject(GameObject object, boolean addAllAsListeners) {
        boolean result = addSceneObject(object);
        if(!addAllAsListeners) {
            return result;
        }

        addAllInputListeners(object);
        return result;
    }

    public synchronized boolean removeSceneObject(GameObject object) {
        removeAllInputListeners(object);
        removeAllRenderables(object);

        return sceneObjects.remove(object);
    }

    public synchronized boolean removeSceneObject(int index) {
        GameObject object = sceneObjects.get(index);
        removeAllInputListeners(object);
        removeAllRenderables(object);

        return sceneObjects.remove(object);
    }

    public synchronized boolean removeSceneObject(GameObject object, boolean dontDestroyListeners){
        if (!dontDestroyListeners) {
            removeAllInputListeners(object);
        }
        removeAllRenderables(object);
        return sceneObjects.remove(object);
    }

    public int getSceneObjectCount() {
        return sceneObjects.size();
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
            Iterator<IScriptable> scripts = object.getScriptables();
            while (scripts.hasNext()){
                IScriptable script = scripts.next();
                script.start(object);
            }
        }
    }

    public void update(double deltaTime){
        for (GameObject object : sceneObjects){
            Iterator<IScriptable> scripts = object.getScriptables();
            while (scripts.hasNext()){
                IScriptable script = scripts.next();
                script.update(object, deltaTime);
            }
        }

    }

    public void physicsUpdate() {
        collisionManager.update(sceneObjects);
    }

    public void stop(){
        for (GameObject object : sceneObjects){
            Iterator<IScriptable> scripts = object.getScriptables();
            while (scripts.hasNext()){
                IScriptable script = scripts.next();
                script.stop(object);
            }
        }
        collisionManager.reset();
    }

    // Soft reset
    public void reset() {
        for (GameObject object : sceneObjects){
            Iterator<IScriptable> scripts = object.getScriptables();
            while (scripts.hasNext()){
                IScriptable script = scripts.next();
                script.reset(object);
            }
        }
    }


    public void render(Graphics2D g2d) {
        // Apply zoom
        g2d.scale(scaleX, scaleY);

        for (Tuple<GameObject, IRenderable> renderable : renderables) {
            renderable.getSecond().render(renderable.getFirst(), this, g2d);
        }

        // Reset the transform after rendering
        g2d.scale(1.0 / scaleX, 1.0 / scaleY);
    }

    public double getScaleX() {
        return scaleX;
    }

    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
    }

    // Getter and setter for scaleY
    public double getScaleY() {
        return scaleY;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
    }

    // endregion

    public boolean isObjectListener(GameObject o) {
        for (Tuple<GameObject, IScriptable> t : inputListeners) {
            if (t.getFirst().equals(o)) {
                return true;
            }
        }
        return false;
    }

    // region Static Methods

    public static Scene NullScene() {
        return new Scene();
    }
    // endregion

    @Override
    public JSONObject serialize() {
        JSONObject output = new JSONObject();
        output.put("name", getName());

        JSONArray scene_objects = new JSONArray();
        for (GameObject o : sceneObjects) {
            JSONObject object = o.serialize();

            boolean isListener = isObjectListener(o);
            object.put("is_listener", isListener);

            scene_objects.put(object);
        }
        output.put("scene_objects", scene_objects);

        return output;
    }

    @Override
    public void deserialize(JSONObject data) {
        JSONArray scene_objects = data.getJSONArray("scene_objects");
        for (Object o : scene_objects) {
            JSONObject object = (JSONObject) o;
            GameObject gameObject = new GameObject();
            gameObject.deserialize(object);

            addSceneObject(gameObject);

            boolean isListener = object.getBoolean("is_listener");
            if (isListener) {
                addAllInputListeners(gameObject);
            }

        }
    }


}
