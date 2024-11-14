package entity;


import com.project.engine.Rendering.SpriteRenderer;

//Separate JumpAnimation
public class JumpAnimation implements AnimationState {
    private static final String[] frames = {"assets/char_jump_straight.png", "assets/char_jump_straight.png"};
    private int frameIndex = 0;
    private int frameDelay = 5; //Controls Animation Speed
    private int frameCounter = 0;

    @Override
    public void onStart(SpriteRenderer renderer, double x, double y) {
        frameIndex = 0;
        frameCounter = 0;

        // Set the initial image and position
        renderer.setImage(frames[frameIndex], (int) x, (int) y);
    }

    @Override
    public void onContinue(SpriteRenderer renderer, double x, double y) {
        frameCounter++;
        if (frameCounter >= frameDelay) {
            frameIndex = (frameIndex + 1) % frames.length;  // Toggle between frames
            frameCounter = 0;

            // Update the sprite image and position
            renderer.setImage(frames[frameIndex], (int) x, (int) y);
        }
    }

    @Override
    public void onEnd(SpriteRenderer renderer, double x, double y) {
        // Optionally reset or set to an idle image
        renderer.setImage("assets/character.png", 128, 128);
    }
}

