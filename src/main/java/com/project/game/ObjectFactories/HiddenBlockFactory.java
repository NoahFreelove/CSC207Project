package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.ILambdaTrigger;
import com.project.game.Scripts.GroundStats;
import com.project.game.Scripts.SimpleCollider;
import com.project.game.Scripts.SimpleTrigger;
import com.project.engine.Physics.Collision.CollisionVolume;
import com.project.engine.Physics.PhysicsBody.RigidBody2D;

public class HiddenBlockFactory extends AbstractObjectFactory {
    private final String DEFAULT_GROUND_ASSET = "assets/used_item_block.png";
    protected HiddenBlockFactory() {
        super();
    }

    public GameObject generate(double x, double y, int z, double width, double height, double friction) {
        return produceGameObject(x, y, z, width, height, friction, DEFAULT_GROUND_ASSET);
    }

    public GameObject generate(double x, double y, int z, double width, double height, String asset) {
        return produceGameObject(x, y, z, width, height, 0.5, asset);
    }

    public GameObject generate(double x, double y, int z, double width, double height, double friction, String asset) {
        return produceGameObject(x, y, z, width, height, friction, asset);
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        return produceGameObject(x, y, z, width, height, 0.5, DEFAULT_GROUND_ASSET);
    }

    protected GameObject produceGameObject(double x, double y, int z, double width, double height, double friction, String asset) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);

        obj.getTransform().update(obj, 0); // This call allows obj to have right tiling scale.
        SpriteRenderer sr = new SpriteRenderer(asset, 64,64);
        sr.setEnabled(false);
        obj.addRenderable(sr);

        obj.addBehavior(new GroundStats(friction));

        SimpleTrigger st = new SimpleTrigger(new ILambdaTrigger() {
            @Override
            public void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
                if(other.hasTag("player") && interactor instanceof SimpleTrigger) {
                    // System.out.println(interactor.getTag());

                    RigidBody2D rb = other.getScriptable(RigidBody2D.class);

                    if (rb == null) {
                        System.err.println("RigidBody2D not found on player");
                        return;
                    }
                    if(rb.getVelocityY() < 0) {
                        obj.addBehavior(new SimpleCollider());
                        obj.addTag("ground");

                        SimpleTrigger st = obj.getScriptable(SimpleTrigger.class);
                        if (st == null) {
                            System.err.println("SimpleTrigger not found on hidden block");
                            return;
                        }
                        obj.removeBehavior(st);
                        sr.setEnabled(true);

                        rb.resetY();
                    }
                }
            }
        });

        st.setRelDimensions(0.1d, 0.01);
        st.setOffset(32,50);
        obj.addBehavior(st);

        return obj;
    }
}
