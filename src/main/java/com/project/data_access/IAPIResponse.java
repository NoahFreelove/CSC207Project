package com.project.data_access;

import org.json.JSONObject;

public interface IAPIResponse {
    default void onResponse(JSONObject res) {}
}
