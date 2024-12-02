package com.project.data_access.player_death_count;

import com.project.database.player_death_count.APIRequest;
import com.project.use_cases.player_death_count.APIDataAccess;

import java.util.HashMap;

public class JokeAPIRequest extends APIRequest implements APIDataAccess {

    public JokeAPIRequest(APIDataAccess responseCallback, JokeType... types) {
        super(responseCallback, formatURL(types), APIMethod.GET, getDefaultBlacklist());
    }

    public JokeAPIRequest(JokeType... types) {
        super(formatURL(types), APIMethod.GET, getDefaultBlacklist());
    }

    private static HashMap<String, String> getDefaultBlacklist() {
        HashMap<String,String> output = new HashMap<>();
        output.put("blacklistFlags", "nsfw,religious,political,racist,sexist,explicit");
        return output;
    }

    private static String formatURL(JokeType... types) {
        if (types.length == 0){
            return "https://v2.jokeapi.dev/joke/Any";
        }
        StringBuilder url = new StringBuilder("https://v2.jokeapi.dev/joke/");
        for (JokeType type : types) {
            url.append(type.toString().toLowerCase()).append(",");
        }
        url.deleteCharAt(url.length() - 1);
        return url.toString();
    }

}
