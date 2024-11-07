package com.project.engine.Core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

import com.project.engine.Rendering.IRenderable;
import com.project.engine.Scripting.IScriptable;
import com.project.engine.Serialization.ISerializable;
import com.project.physics.PhysicsBody.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Base class for all Game Objects that have behavior.
 */
public final class GameObject implements ISerializable {

    // region Static Variables
    // Global counter for ensuring unique values
    private static final AtomicLong GLOBAL_COUNTER = new AtomicLong(0);

    // endregion

    // region Instance Variables

    // Names are not necessarily unique but are helpful to keep track of objects.
    private String name = "GameObject";

    // Tags can be used to broadly classify GameObjects. Eg. you can check, regardless of name, if
    // an object is tagged as "enemy".
    private String tag = "Untagged";

    // UID (Unique ID) is a guaranteed unique identifier each object is issued.
    // UIDs are unique even across scenes, i.e, if you reload a scene every object will have a new UID
    // This means it could overflow, but that's 2^64 objects (18,400,000,000,000,000,000).
    // You probably have larger issues...
    private final long uID = requestUid();
    private Transform transform;

    /**
     * The behaviors attached to this GameObject. Each one will be run every update (not necessarily only at render).
     */
    private final ArrayList<IScriptable> scriptables = new ArrayList<>();

    /**
     * The renderables attached to this GameObject. Each one will be rendered every frame.
     * Realistically, you should only have one renderable per GameObject. But, you can have more if you want.
     */
    private final ArrayList<IRenderable> renderables = new ArrayList<>();

    // endregion

    // region Variety of Constructors
    public GameObject() {
        this.transform = new Transform(this, new Tuple<>(0.0, 0.0));
        addBehavior(transform);
    }

    public GameObject(String name){
        this.name = name;
        this.transform = new Transform(this, new Tuple<>(0.0, 0.0));
        addBehavior(transform);
    }

    public GameObject(String name, double x, double y){
        this.name = name;
        this.transform = new Transform(this, new Tuple<>(x, y));
        addBehavior(transform);
    }

    public GameObject(String name, Tuple<Double, Double> position){
        this.name = name;
        this.transform = new Transform(this, position);
        addBehavior(transform);
    }

    public GameObject(String name, double x, double y, String tag){
        this.name = name;
        this.transform = new Transform(this, new Tuple<>(x, y));
        this.tag = tag;
        addBehavior(transform);
    }

    public GameObject(String name, Tuple<Double, Double> position, String tag){
        this.name = name;
        this.transform = new Transform(this, position);
        this.tag = tag;
        addBehavior(transform);
    }

    public GameObject(String name, String tag){
        this.name = name;
        this.transform = (new Transform(this, new Tuple<>(0.0, 0.0)));
        this.tag = tag;
        addBehavior(transform);
    }
    // endregion

    // region Getters & Setters
    /**
     * Get the GameObject Name.
     * @return gameObject's Name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the GameObject tag.
     * @return gameObject's Tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Get the GameObject UID.
     * @return The gameObject's UID
     */
    public long getUid() {
        return uID;
    }

    public Transform getTransform() { return transform; }
    /**
     * Set object's name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set object's tag.
     * @param tag the tag to set
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Get all behaviors attached to this GameObject.
     * @return an iterator of all behaviors
     */
    public Iterator<IScriptable> getScriptables() {
        return scriptables.iterator();
    }

    public Iterator<IRenderable> getRenderables() {
        return renderables.iterator();
    }

    public <T extends IScriptable> T getScriptable(Class<T> objClass) {
        for (IScriptable scriptable : scriptables) {
            if (objClass.isInstance(scriptable)) {
                return (T) scriptable;
            }
        }

        return null;
    }
    // endregion

    // region Adders & Removers
    /**
     * Add a behavior to this GameObject.
     * @param script the behavior to add
     * @return true if the behavior was added, false if it was already present
     */
    public synchronized boolean addBehavior(IScriptable script){
        if (scriptables.contains(script))
            return false;
        return scriptables.add(script);
    }

    /**
     * Remove a behavior from this GameObject.
     * @param script the behavior to remove
     * @return true if the behavior was removed, false if it was not present
     */
    public synchronized boolean removeBehavior(IScriptable script){
        if(scriptables.contains(script)) {
            return scriptables.remove(script);
        }
        return false;
    }

    public synchronized boolean addRenderable(IRenderable renderable){
        if (renderables.contains(renderable))
            return false;
        return renderables.add(renderable);
    }

    public synchronized boolean removeRenderable(IRenderable renderable){
        if (renderables.contains(renderable)) {
            return renderables.remove(renderable);
        }
        return false;
    }
    // endregion

    // region Boolean Methods
    public boolean hasScript(IScriptable listenerScript) {
        return scriptables.contains(listenerScript);
    }

    public boolean hasRenderable(IRenderable renderable) {
        return renderables.contains(renderable);
    }

    // endregion

    // region Built-ins
    @Override
    public String toString() {
        return "GameObject{" +
                "name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", uID=" + uID +
                '}';
    }
    // endregion


    // region Static Methods
    /**
     * Get a new UID.
     * @return a new, unique UID
     */
    private static long requestUid() {
        return GLOBAL_COUNTER.getAndIncrement();
    }

    @Override
    public JSONObject serialize() {
        JSONObject object = new JSONObject();
        object.put("name", getName());
        object.put("tag", getTag());
        object.put("transform", getTransform().serialize());

        JSONArray scriptablesJson = new JSONArray();
        for (IScriptable r : scriptables) {
            if(r.getClass().equals(Transform.class))
                continue;

            JSONObject serialized = r.serialize();
            if (serialized == null) {
                continue;
            }
            serialized.put("class", r.getClass().getName());

            scriptablesJson.put(serialized);
        }
        object.put("scriptables", scriptablesJson);


        JSONArray renderablesJson = new JSONArray();
        for (IRenderable r : renderables) {
            JSONObject serialized = r.serialize();
            serialized.put("class", r.getClass().getName());
            renderablesJson.put(serialized);

        }
        object.put("renderables", renderablesJson);

        return object;
    }

    @Override
    public void deserialize(JSONObject data) {
        setName(data.getString("name"));
        setTag(data.getString("tag"));
        transform.deserialize(data.getJSONObject("transform"));
        JSONArray scriptablesJson = data.getJSONArray("scriptables");
        for (Object o : scriptablesJson) {
            if (! (o instanceof JSONObject)) {
                System.err.println("Found non-JSONObject in scriptables JSONArray");
                continue;
            }
            JSONObject obj = (JSONObject) o;
            String classNameString = obj.getString("class");
            Class<?> classInst = null;

            try {
                classInst = Class.forName(classNameString);
            } catch (ClassNotFoundException e) {
                System.err.println("Error deserializing scriptable:  (" + e.getClass().getCanonicalName() + ")" + e.getMessage());
                continue;
            }

            if(classInst.equals(Transform.class))
                continue;

            // instantiate the class default constructor with reflection
            try {
                IScriptable script = (IScriptable) classInst.getDeclaredConstructor().newInstance();
                script.deserialize(obj);
                addBehavior(script);
            } catch (Exception e) {
                System.err.println("Error deserializing scriptable: (" + e.getClass().getCanonicalName() + ")" + e.getMessage());
                continue;
            }
        }

        JSONArray renderablesJson = data.getJSONArray("renderables");
        for (Object o : renderablesJson) {
            if (! (o instanceof JSONObject)) {
                System.err.println("Found non-JSONObject in renderables JSONArray");
                continue;
            }
            JSONObject obj = (JSONObject) o;

            Class<?> classInst = null;
            try {
                classInst = Class.forName(obj.getString("class"));
            } catch (ClassNotFoundException e) {
                System.err.println("Error deserializing renderable: (" + e.getClass().getCanonicalName() + ")" + e.getMessage());
                continue;
            }

            // instantiate the class default constructor with reflection
            try {
                IRenderable renderable = (IRenderable) classInst.getDeclaredConstructor().newInstance();
                renderable.deserialize(obj);
                addRenderable(renderable);
            } catch (Exception e) {
                System.err.println("Error deserializing renderable: (" + e.getClass().getCanonicalName() + ")" + e.getMessage());
                continue;
            }
        }


    }
    // endregion
}
