package com.project.game.Scripts;

import com.project.engine.Network.JokeAPIRequest;
import com.project.engine.Network.JokeType;
import com.project.engine.Scripting.IScriptable;

public class DeathJoke implements IScriptable {
    private String jokeText;
    public DeathJoke() {}

    public void generateJoke() {
        JokeAPIRequest joke = new JokeAPIRequest(JokeType.Programming, JokeType.Any);

        if (joke.getResponseDefaultIfNull().has("joke")) {
            jokeText = (String)joke.getResponseDefaultIfNull().get("joke");
        }
        else{
            jokeText = joke.getResponseDefaultIfNull().get("setup") + "\n"
                        + joke.getResponseDefaultIfNull().get("delivery");
        }
    }

    public void readJoke() {
        System.out.println(jokeText);
    }

}
