<<<<<<<< HEAD:src/main/java/com/project/data_access/player_death_count/JokeAPIRequest.java
package com.project.data_access.player_death_count;

import com.project.database.player_death_count.APIRequest;
import com.project.use_cases.player_death_count.APIDataAccess;

========
package com.project.database.player_death_count.endpoints;

import com.project.data_access.player_death_count.APIMethod;
import com.project.data_access.player_death_count.IAPIResponse;
import com.project.data_access.player_death_count.JokeType;
import com.project.database.player_death_count.APIRequest;
import com.project.database.player_death_count.endpoints.JokeAPIAboutRequest;
>>>>>>>> e98bde163f6a6000d5e2665d4c2b236116c01a76:src/main/java/com/project/database/player_death_count/endpoints/JokeAPIRequest.java
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
