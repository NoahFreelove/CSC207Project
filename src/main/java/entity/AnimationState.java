package entity;

import com.project.engine.Rendering.SpriteRenderer;

public interface AnimationState {
    void onStart(SpriteRenderer renderer, int x, int y);   // Pass position
    void onContinue(SpriteRenderer renderer, int x, int y);
    void onEnd(SpriteRenderer renderer, int x, int y);
}
