package com.project.database;

import com.project.data_access.APIMethod;
import com.project.data_access.IAPIResponse;
import com.project.data_access.JokeType;

import java.util.HashMap;

public class JokeAPIRequest extends APIRequest {

    public JokeAPIRequest(IAPIResponse responseCallback, JokeType... types) {
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
