package com.project.use_cases.general;

import com.project.entity.core.GameObject;
import com.project.entity.core.Scene;
import com.project.entity.rendering.SpriteRenderer;
import com.project.use_cases.play_prebuilt_levels.scripts.LoadScript;

public class LoadingScreen {
    public static void addLoadingScreen(Scene parent) {
        GameObject out = createLoadingScreen(parent);
        parent.addSceneObject(out);
    }

    private static GameObject createLoadingScreen(Scene parent) {
        GameObject output = new GameObject();
        SpriteRenderer sr = new SpriteRenderer("assets/loading.png", 800, 800);
        sr.setIndependentOfCamera(true);
        output.getTransform().setZIndex(1000);
        output.getTransform().setScale(1/ parent.getScaleX(), 1/ parent.getScaleY());
        output.addRenderable(sr);
        output.addBehavior(new LoadScript());
        return output;
    }
}
