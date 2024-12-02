package com.project.app;

import com.project.data_access.player_death_count.JokeAPIRequest;
import com.project.database.player_death_count.endpoints.JokeAPIAboutRequest;
import com.project.use_cases.core.game.GameInteractor;
import com.project.entity.core.Scene;
import com.project.entity.core.Tuple;
import com.project.use_cases.core.game.GameOutputData;
import com.project.use_cases.core.prebuilts.scenes.MainMenuFactory;
import com.project.external_interfaces.player_death_count.PlayTTS;
import org.json.JSONArray;
import org.json.JSONObject;

public class PlayGame {
    public static void main(String[] args) {
        Tuple<GameInteractor, GameOutputData> game = GameInteractor.createAndWait();
        GameInteractor e = game.getFirst();
        GameOutputData w = game.getSecond();
        e.closeHook = PlayTTS::shutdown;

        Scene main = MainMenuFactory.createScene();
        w.setActiveScene(main);
    }
}