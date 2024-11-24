package com.project.game.Scripts;

import com.project.engine.Network.APIResponse;
import com.project.engine.Network.JokeAPIRequest;
import com.project.engine.Network.JokeType;
import com.project.engine.Scripting.IScriptable;
import com.project.game.tts.PlayTTS;
import org.json.JSONObject;

public class DeathJoke implements IScriptable {
    private String jokeText = "";
    public DeathJoke() {}

    public void generateJoke() {
        new JokeAPIRequest(new APIResponse() {
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
