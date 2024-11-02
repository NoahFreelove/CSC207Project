package com.project.engine.Serialization;

import org.json.JSONObject;

/**
 * Why not use java.io.Serializable? Because it's not as flexible as we need it to be!
 * IMPORTANT: If you want serialization, you must have a default constructor! (i.e one with no parameters)
 * Why? Because we use java reflection on the default constructor, then call deserialize.
 */
public interface ISerializable {
    default JSONObject serialize() { return null; } // 😎😎😎
    default void deserialize(JSONObject data) { } // 😎😎😎
    default Class<?> attachedClass(){
        return getClass();
    }

}
