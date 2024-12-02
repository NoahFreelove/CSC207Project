package com.project.use_cases.player_death_count;

import com.project.external_interfaces.player_death_count.PlayTTS;

public interface TTSDataAccess {
    default void playTTS(String text) {
        PlayTTS.say(text);
    }
}
