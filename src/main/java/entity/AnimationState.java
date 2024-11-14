package entity;

import com.project.engine.Rendering.SpriteRenderer;

public interface AnimationState {
    void onStart(SpriteRenderer renderer, double x, double y);   // Pass position
    void onContinue(SpriteRenderer renderer, double x, double y);
    void onEnd(SpriteRenderer renderer, double x, double y);
}
