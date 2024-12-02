package com.project.use_cases.player_death_count;

import com.project.data_access.player_death_count.JokeAPIRequest;
import com.project.data_access.player_death_count.JokeType;

import org.json.JSONObject;

public interface APIDataAccess {
    default void onResponse(JSONObject res) {}
    default void fetch() {
        new JokeAPIRequest(this, JokeType.Programming, JokeType.Any);
    }
}
