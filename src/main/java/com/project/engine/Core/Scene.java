package com.project.engine.Core;

import com.project.engine.Scripting.IScriptable;

import java.util.ArrayList;
import java.util.Iterator;

public class Scene {

    private final String name;

    private final ArrayList<GameObject> sceneObjects = new ArrayList<>();

    private final ArrayList<IScriptable> inputListeners = new ArrayList<>();


    public Scene() {
        this.name = "Empty Scene";
    }

    public void addListener(IScriptable listenerScript){
        if (inputListeners.contains(listenerScript))
            return;
        inputListeners.add(listenerScript);
    }

    public void addAllListeners(GameObject object){
        Iterator<IScriptable> scripts = object.getBehaviors();
        while (scripts.hasNext()){
            IScriptable script = scripts.next();
            addListener(script);
        }
    }

    public void removeListener(IScriptable listenerScript){
        inputListeners.remove(listenerScript);
    }

    public String getName() {
        return name;
    }

    // region Static Methods

    public static Scene NullScene() {
        return new Scene();
    }

    // endregion
}
