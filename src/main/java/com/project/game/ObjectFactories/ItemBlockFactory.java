package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.engine.Physics.Collision.CollisionVolume;
import com.project.engine.Physics.PhysicsBody.RigidBody2D;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.ILambdaTrigger;
import com.project.game.Scripts.GroundStats;
import com.project.game.Scripts.SimpleCollider;
import com.project.game.Scripts.SimpleTrigger;

public class ItemBlockFactory extends AbstractObjectFactory {
    private final String ACTIVATED_ASSET = "assets/used_item_block.png";
    private final String BASE_ASSET = "assets/item_block.png";

    protected ItemBlockFactory() {
        super();
    }

    public GameObject generate(double x, double y, int z, double width, double height, double friction) {
        return produceGameObject(x, y, z, width, height, friction, BASE_ASSET);
    }

    public GameObject generate(double x, double y, int z, double width, double height, String asset) {
        return produceGameObject(x, y, z, width, height, 0.5, asset);
    }

    public GameObject generate(double x, double y, int z, double width, double height, double friction, String asset) {
        return produceGameObject(x, y, z, width, height, friction, asset);
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        return produceGameObject(x, y, z, width, height, 0.5, BASE_ASSET);
    }

    protected GameObject produceGameObject(double x, double y, int z, double width, double height, double friction, String asset) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);

        obj.getTransform().update(obj, 0);
        SpriteRenderer sr = new SpriteRenderer(asset, 64,64);
        obj.addRenderable(sr);

        obj.addBehavior(new GroundStats(friction));
        SimpleCollider sc = new SimpleCollider();
        sc.setRelDimensions(1,0.8);
        obj.addBehavior(sc);
        obj.addTag("ground");

        SimpleTrigger st = new SimpleTrigger(new ILambdaTrigger() {
            @Override
            public void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
                if(other.hasTag("player") && interactor instanceof SimpleTrigger) {

                    RigidBody2D rb = other.getScriptable(RigidBody2D.class);

                    if (rb == null) {
                        System.err.println("RigidBody2D not found on player");
                        return;
                    }
                    if(rb.getVelocityY() < 0) {

                        SimpleTrigger st = obj.getScriptable(SimpleTrigger.class);
                        if (st == null) {
                            System.err.println("SimpleTrigger not found on item block");
                            return;
                        }
                        obj.removeBehavior(st);
                        sr.setImage(ACTIVATED_ASSET, 64, 64);

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
