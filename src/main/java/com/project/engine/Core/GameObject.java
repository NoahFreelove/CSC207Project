package com.project.engine.Core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

import com.project.engine.Scripting.IScriptable;

/**
 * Base class for all Game Objects that have behavior.
 */
public class GameObject {

    // Global counter for ensuring unique values
    private static final AtomicLong GLOBAL_COUNTER = new AtomicLong(0);

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

    private ArrayList<IScriptable> behaviors = new ArrayList<>();

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

    public Iterator<IScriptable> getBehaviors() {
        return behaviors.iterator();
    }

    public boolean addBehavior(IScriptable script){
        if (behaviors.contains(script))
            return false;
        return behaviors.add(script);
    }

    public boolean removeBehavior(IScriptable script){
        if(behaviors.contains(script))
            return behaviors.remove(script);
        return false;
    }

    /**
     * Get a new UID.
     * @return a new, unique UID
     */
    private static long requestUid() {
        return GLOBAL_COUNTER.getAndIncrement();
    }
}
