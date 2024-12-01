package com.project.data_access.player_death_count;

import org.json.JSONObject;

public interface IAPIResponse {
    default void onResponse(JSONObject res) {}
}
