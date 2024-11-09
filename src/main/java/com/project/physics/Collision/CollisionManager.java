package com.project.physics.Collision;

import com.project.engine.Core.GameObject;
import com.project.physics.Collision.Types.ECollisionShape;
import com.project.physics.Collision.Types.ECollisionVolume;
import com.project.physics.PhysicsBody.Transform;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CollisionManager {
    HashMap<String, Boolean> collided = new HashMap<>();

    public CollisionManager() {}

    private boolean checkBoxCollision(GameObject o1, GameObject o2, CollisionVolume col1, CollisionVolume col2) {
        Transform t1 = o1.getTransform();
        Transform t2 = o2.getTransform();

        double x1 = t1.getPositionX() + col1.getXOffset(), x2 = t2.getPositionX() + col2.getXOffset(),
               y1 = t1.getPositionY() + col1.getYOffset(), y2 = t2.getPositionY() + col2.getYOffset(),
               w1 = Math.abs(t1.getWidth()) * col1.getRelWidth(), w2 =  Math.abs(t2.getWidth()) * col2.getRelWidth(),
               h1 = Math.abs(t1.getHeight()) * col1.getRelHeight(), h2 = Math.abs(t2.getHeight()) * col2.getRelHeight();

        return x1 <= x2 + w2 && x1 + w1 >= x2 && y1 <= y2 + h2 && y1 + h1 >= y2;
    }

    private void sendCollisionEvents(boolean intersect, String key, GameObject object1, GameObject object2,
                                     CollisionVolume o1Col, CollisionVolume o2Col) {
        if (!intersect && collided.get(key)) {
            if (o1Col.volumeType() == ECollisionVolume.COLLIDER) {
                ((Collider)o1Col).onCollisionEnter(object1, object2, o2Col);
            }
            else {
                ((Trigger)o1Col).onTriggerEnter(object1, object2, o2Col);
            }

            if (o2Col.volumeType() == ECollisionVolume.COLLIDER) {
                ((Collider)o2Col).onCollisionEnter(object2, object1, o1Col);
            }
            else {
                ((Trigger)o2Col).onTriggerEnter(object2, object1, o1Col);
            }
        }
        else if (intersect && !collided.get(key)) {
            if (o1Col.volumeType() == ECollisionVolume.COLLIDER) {
                ((Collider)o1Col).onCollisionExit(object1, object2, o2Col);
            }
            else {
                ((Trigger)o1Col).onTriggerExit(object1, object2, o2Col);
            }

            if (o2Col.volumeType() == ECollisionVolume.COLLIDER) {
                ((Collider)o2Col).onCollisionExit(object2, object1, o1Col);
            }
            else {
                ((Trigger)o2Col).onTriggerExit(object2, object1, o1Col);
            }
        }
        else if (intersect) {
            if (o1Col.volumeType() == ECollisionVolume.COLLIDER) {
                ((Collider)o1Col).onCollisionContinue(object1, object2, o2Col);
            }
            else {
                ((Trigger)o1Col).onTriggerContinue(object1, object2, o2Col);
            }

            if (o2Col.volumeType() == ECollisionVolume.COLLIDER) {
                ((Collider)o2Col).onCollisionContinue(object2, object1, o1Col);
            }
            else {
                ((Trigger)o2Col).onTriggerContinue(object2, object1, o1Col);
            }
        }
    }
    private void updateCollision(GameObject object1, GameObject object2) {
        Iterator<CollisionVolume> colliders1 = object1.getCollidables();
        while (colliders1.hasNext()){
            CollisionVolume o1Col = colliders1.next();

            Iterator<CollisionVolume> colliders2 = object2.getCollidables();

            while (colliders2.hasNext()){
                CollisionVolume o2Col = colliders2.next();
                String colKey = object1.getUid() + o1Col.hashCode() + ", " + object2.getUid() + o2Col.hashCode();
                boolean intersect = collided.getOrDefault(colKey, false);

                if (o1Col.volumeShape() == o2Col.volumeShape() && o2Col.volumeShape() == ECollisionShape.BOX) {
                    collided.put(colKey, checkBoxCollision(object1, object2, o1Col, o2Col));
                }
                else {
                    collided.put(colKey, false);
                    continue;
                }

                sendCollisionEvents(intersect, colKey, object1, object2, o1Col, o2Col);
            }
        }
    }

    public void update(List<GameObject> SceneObjects) {
        for (int i = 0; i < SceneObjects.size(); i++) {
            GameObject object1 = SceneObjects.get(i);

            for (int j = i + 1; j < SceneObjects.size(); j++) {
                GameObject object2 = SceneObjects.get(j);

                if (object1 != null && object2 != null) {
                    updateCollision(object1, object2);
                }
            }
        }
    }
}
