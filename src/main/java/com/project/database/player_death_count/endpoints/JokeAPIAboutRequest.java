package com.project.database.player_death_count.endpoints;

import com.project.data_access.player_death_count.APIMethod;
import com.project.data_access.player_death_count.IAPIResponse;
import com.project.database.player_death_count.APIRequest;

import java.util.HashMap;

public class JokeAPIAboutRequest extends APIRequest {
    public JokeAPIAboutRequest(IAPIResponse responseCallback) {
        super(responseCallback, "https://v2.jokeapi.dev/info", APIMethod.GET, new HashMap<>());
    }
}
