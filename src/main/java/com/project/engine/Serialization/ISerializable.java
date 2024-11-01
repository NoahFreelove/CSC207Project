package com.project.engine.Serialization;

import org.json.JSONObject;

/**
 * Why not use java.io.Serializable? Because it's not as flexible as we need it to be!
 */
public interface ISerializable {
    JSONObject serialize(); // 😎😎😎
    void deserialize(JSONObject data); // 😎😎😎

}
