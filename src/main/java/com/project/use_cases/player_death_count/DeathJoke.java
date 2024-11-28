package com.project.use_cases.player_death_count;

import com.project.data_access.IAPIResponse;
import com.project.database.JokeAPIRequest;
import com.project.data_access.JokeType;
import com.project.entity.scripting.IScriptable;
import com.project.external_interfaces.PlayTTS;
import org.json.JSONObject;

public class DeathJoke implements IScriptable {
    private String jokeText = "";
    public DeathJoke() {}

    public void generateJoke() {
        new JokeAPIRequest(new IAPIResponse() {
            @Override
            public void onResponse(JSONObject res) {
                if (res.has("joke")) {
                    jokeText = (String) res.get("joke");
                } else {
                    jokeText = res.get("setup") + "\n"
                            + res.get("delivery");
                }

            }
        }, JokeType.Programming, JokeType.Any);
    }

    public void readJoke() {
        System.out.println(jokeText);
        PlayTTS.say(jokeText);

    }

    @Override
    public JSONObject serialize() {
        return new JSONObject();
    }
}
