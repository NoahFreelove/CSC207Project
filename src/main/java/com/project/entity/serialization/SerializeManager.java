package com.project.entity.serialization;

import com.project.entity.core.Scene;
import org.json.JSONObject;


public class SerializeManager {

    public static Scene deserialize(String jsonData) {
        return deserialize(new JSONObject(jsonData));
    }

    public static Scene deserialize(JSONObject jsonData) {
        Scene output = new Scene();
        output.deserialize(jsonData.getJSONObject("scene_data"));
        return output;
    }

    public static JSONObject serialize(Scene scene) {
        JSONObject output = new JSONObject();
        try {
            output.put("scene_data", scene.serialize());
        }
        catch (Exception e) {
            System.err.println("Error occurred during serialization: " + e.getMessage());
            return new JSONObject();
        }
        return output;
    }
}
