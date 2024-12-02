package com.project.use_cases.player_move;


import com.project.entity.animation.AnimationState;
import com.project.entity.rendering.SpriteRenderer;

public class JumpAnimation implements AnimationState {

    @Override
    public void onStart(SpriteRenderer renderer, double x, double y) {
        renderer.setImage("assets/char_jump_straight.png", (int) x, (int) y);
    }

    @Override
    public void onContinue(SpriteRenderer renderer, double x, double y) {
        renderer.setImage("assets/char_jump_straight.png", (int) x, (int) y);
    }

    @Override
    public void onEnd(SpriteRenderer renderer, double x, double y) {
        // Optionally reset or set to an idle image
        renderer.setImage("assets/character.png", 128, 128);
    }
}

