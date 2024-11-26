package com.project.engine.Physics.Collision;

import com.project.engine.Core.GameObject;
import com.project.engine.Physics.Collision.Types.ECollisionShape;
import com.project.engine.Physics.Collision.Types.ECollisionVolume;
import com.project.engine.Physics.PhysicsBody.Transform;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CollisionManager {
    private final HashMap<String, Boolean> collided = new HashMap<>();

    public CollisionManager() {}

    private boolean checkBoxCollision(GameObject o1, GameObject o2, CollisionVolume col1, CollisionVolume col2) {
        Transform t1 = o1.getTransform();
        Transform t2 = o2.getTransform();

        double x1 = t1.getPositionX() + col1.getXOffset();
        double y1 = t1.getPositionY() + col1.getYOffset();
        double w1 = Math.abs(t1.getWidth()) * col1.getRelWidth();
        double h1 = Math.abs(t1.getHeight()) * col1.getRelHeight();

        double x2 = t2.getPositionX() + col2.getXOffset();
        double y2 = t2.getPositionY() + col2.getYOffset();
        double w2 = Math.abs(t2.getWidth()) * col2.getRelWidth();
        double h2 = Math.abs(t2.getHeight()) * col2.getRelHeight();

        // tolerance value (in pixels)
        double tolerance = 0.0;

        y1 -= tolerance / 2;
        h1 += tolerance;

        y2 -= tolerance / 2;
        h2 += tolerance;

        return x1 < x2 + w2 && x1 + w1 > x2 &&
                y1 < y2 + h2 && y1 + h1 > y2;
    }

    private void sendCollisionEvents(boolean previousIntersect, String key, GameObject object1, GameObject object2,
                                     CollisionVolume o1Col, CollisionVolume o2Col) {
        try{
            if(key == null)
                return;

            boolean currentIntersect = collided.get(key);

            if (!previousIntersect && currentIntersect) {
                triggerCollisionEnterEvents(object1, object2, o1Col, o2Col);
            } else if (previousIntersect && !currentIntersect) {
                triggerCollisionExitEvents(object1, object2, o1Col, o2Col);
            } else if (currentIntersect) {
                triggerCollisionContinueEvents(object1, object2, o1Col, o2Col);
            }
        }
        catch (NullPointerException e) {
            // We get a null pointer sometimes from thread race conditions, it is far
            // faster and simpler to ignore the exception than to synchronize the threads.
            //System.err.println("physics manager nullptr");
        }
    }

    private void triggerCollisionEnterEvents(GameObject object1, GameObject object2,
                                             CollisionVolume o1Col, CollisionVolume o2Col) {
        if (o1Col.volumeType() == ECollisionVolume.COLLIDER) {
            ((Collider) o1Col).onCollisionEnter(object1, object2, o2Col);
        } else {
            ((Trigger) o1Col).onTriggerEnter(object1, object2, o2Col);
        }

        if (o2Col.volumeType() == ECollisionVolume.COLLIDER) {
            ((Collider) o2Col).onCollisionEnter(object2, object1, o1Col);
        } else {
            ((Trigger) o2Col).onTriggerEnter(object2, object1, o1Col);
        }
    }

    private void triggerCollisionExitEvents(GameObject object1, GameObject object2,
                                            CollisionVolume o1Col, CollisionVolume o2Col) {
        if (o1Col.volumeType() == ECollisionVolume.COLLIDER) {
            ((Collider) o1Col).onCollisionExit(object1, object2, o2Col);
        } else {
            ((Trigger) o1Col).onTriggerExit(object1, object2, o2Col);
        }

        if (o2Col.volumeType() == ECollisionVolume.COLLIDER) {
            ((Collider) o2Col).onCollisionExit(object2, object1, o1Col);
        } else {
            ((Trigger) o2Col).onTriggerExit(object2, object1, o1Col);
        }
    }

    private void triggerCollisionContinueEvents(GameObject object1, GameObject object2,
                                                CollisionVolume o1Col, CollisionVolume o2Col) {
        if (o1Col.volumeType() == ECollisionVolume.COLLIDER) {
            ((Collider) o1Col).onCollisionContinue(object1, object2, o2Col);
        } else {
            ((Trigger) o1Col).onTriggerContinue(object1, object2, o2Col);
        }

        if (o2Col.volumeType() == ECollisionVolume.COLLIDER) {
            ((Collider) o2Col).onCollisionContinue(object2, object1, o1Col);
        } else {
            ((Trigger) o2Col).onTriggerContinue(object2, object1, o1Col);
        }
    }

    private void updateCollision(GameObject object1, GameObject object2) {
        Iterator<CollisionVolume> colliders1 = object1.getCollidables();
        while (colliders1.hasNext()) {
            CollisionVolume o1Col = colliders1.next();

            if (o1Col == null) {
                continue;
            }

            Iterator<CollisionVolume> colliders2 = object2.getCollidables();
            while (colliders2.hasNext()) {
                CollisionVolume o2Col = colliders2.next();

                if (o2Col == null) {
                    continue;
                }

                String colKey = object1.getUid() + "-" + o1Col.hashCode() + ":" + object2.getUid() + "-" + o2Col.hashCode();

                boolean previousIntersect = collided.getOrDefault(colKey, false);
                boolean currentIntersect = false;

                if (o1Col.volumeShape() == o2Col.volumeShape() && o1Col.volumeShape() == ECollisionShape.BOX) {
                    currentIntersect = checkBoxCollision(object1, object2, o1Col, o2Col);
                } else {
                    currentIntersect = false;
                }

                collided.put(colKey, currentIntersect);

                sendCollisionEvents(previousIntersect, colKey, object1, object2, o1Col, o2Col);
            }
        }
    }

    public void update(List<GameObject> sceneObjects) {
        for (int i = 0; i < sceneObjects.size(); i++) {
            GameObject object1 = sceneObjects.get(i);

            if (object1 == null) continue;

            for (int j = i + 1; j < sceneObjects.size(); j++) {
                GameObject object2 = sceneObjects.get(j);

                if (object2 == null) continue;

                updateCollision(object1, object2);
            }
        }
    }

    public void reset() {
        collided.clear();
    }
}
