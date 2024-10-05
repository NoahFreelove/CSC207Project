package com.project;

import com.project.engine.Network.APIResponse;
import com.project.engine.Network.JokeAPIRequest;
import com.project.engine.Network.JokeType;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        System.out.println(new JokeAPIRequest(JokeType.Programming, JokeType.Any).getResponseDefaultIfNull());
        System.out.println("This was synchronous");
        new JokeAPIRequest(new APIResponse() {
            @Override
            public void onResponse(JSONObject res) {
                System.out.println(res);
            }
        }, JokeType.Programming, JokeType.Any);
        System.out.println("This was asynchronous");
    }
}