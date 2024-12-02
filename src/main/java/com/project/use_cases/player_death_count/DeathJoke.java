package com.project.use_cases.player_death_count;

import com.project.entity.scripting.IScriptable;
import org.json.JSONObject;

public class DeathJoke implements IScriptable {
    private String jokeText = "";

    public DeathJoke() {}

    public void generateJoke() {
       new APIDataAccess() {
           @Override
           public void onResponse(JSONObject res) {
               if (res.has("joke")) {
                   jokeText = (String) res.get("joke");
               } else {
                   jokeText = res.get("setup") + "\n"
                           + res.get("delivery");
               }
           }
       }.fetch();
    }

    public void readJoke() {
        System.out.println(jokeText);
        new TTSDataAccess(){}.playTTS(jokeText);
    }

    @Override
    public JSONObject serialize() {
        return new JSONObject();
    }
}
