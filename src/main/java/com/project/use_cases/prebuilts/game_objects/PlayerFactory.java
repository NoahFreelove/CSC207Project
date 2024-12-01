package com.project.use_cases.prebuilts.game_objects;

import com.project.entity.core.GameObject;
import com.project.entity.rendering.SpriteRenderer;
import com.project.entity.physics.physics_body.RigidBody2D;
import com.project.use_cases.play_prebuilt_levels.scripts.SpawnPoint;
import com.project.use_cases.play_prebuilt_levels.scripts.SimpleCollider;
import com.project.use_cases.play_prebuilt_levels.scripts.SimpleTrigger;
import com.project.use_cases.player_death_count.DeathJoke;
import com.project.use_cases.player_death_count.PlayerDeathInteractor;
import com.project.use_cases.player_move.GroundTrigger;
import com.project.use_cases.player_move.PlayerMoveInteractor;

public class PlayerFactory extends AbstractObjectFactory {
    protected PlayerFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        obj.addTag("player");

        float factor = 1.5f;
        float tmp = 128/factor;

        SpriteRenderer sr = new SpriteRenderer("assets/character.png", (int)(tmp),(int)(tmp));
        sr.setIndependentOfCamera(false);
        obj.addRenderable(sr);

        obj.addBehavior(new SpawnPoint(x, y));
        obj.addBehavior(new PlayerDeathInteractor());
        obj.addBehavior(new DeathJoke());
        obj.addBehavior(new PlayerMoveInteractor());
        var rb = new RigidBody2D();
        obj.addBehavior(rb);
        SimpleCollider sc = new SimpleCollider(tmp/5, tmp*0.15, 0.6, 0.7);
        obj.addBehavior(sc);
        GroundTrigger gt = new GroundTrigger(tmp/4,tmp*0.8, 0.5, 0.05);
        gt.setTag("PlayerGround");

        obj.addBehavior(gt);

        // add head trigger
        SimpleTrigger st = new SimpleTrigger();
        st.setTag("playerHead");

        st.setOffset(0, -1/2f);
        st.setRelDimensions(1, 0.2f/2f);
        obj.addBehavior(st);

        return obj;
    }
}
