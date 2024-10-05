package com.project.engine.Network;

import org.json.JSONObject;

public interface APIResponse {
    default void onResponse(JSONObject res) {}
}
