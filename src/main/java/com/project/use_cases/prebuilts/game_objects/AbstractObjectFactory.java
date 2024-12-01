package com.project.use_cases.prebuilts.game_objects;

import com.project.entity.core.GameObject;
import com.project.use_cases.prebuilts.game_objects.game_object_types.ObjectType;

public class AbstractObjectFactory {
    protected AbstractObjectFactory() {}

    /**
     * To create a new object factory, override this method with desired behaviour.
     * @param x x location
     * @param y y location
     * @param z z level
     * @param width object width
     * @param height object height
     * @return the new game object
     */
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        GameObject obj = new GameObject();
        obj.getTransform().setPosition(x, y);
        obj.getTransform().setScale(width, height);
        obj.getTransform().setZIndex(z);

        return obj;
    }

    public static AbstractObjectFactory makeFactory(ObjectType objType) {
        switch (objType) {
            case PLAYER:
                return new PlayerFactory();
            case GROUND_BLOCK:
                return new GroundBlockFactory();
            case ICE_BLOCK:
                return new IceBlockFactory();
            case CLOUD:
                return new CloudFactory();
            case CLOUD_ENEMY:
                return new EnemyCloudFactory();
            case MOVEMENT_PLATFORM:
                return new MovePlatformFactory();
            case HIDDEN_BLOCK:
                return new HiddenBlockFactory();
            case ITEM_BLOCK:
                return new ItemBlockFactory();
            case HIDDEN_SPIKE:
                return new HiddenSpikeFactory();
            case SPIKE:
                return new SpikeFactory();
            case BACKGROUND:
                return new BackgroundFactory();
            case ESCAPE_KEY:
                return new EscapeKeyDetectorFactory();
            case VOID:
                return new VoidFactory();
            case BOUNDARY:
                return new BoundaryFactory();
            case ENEMY:
                return new EnemyFactory();
            case PAUSE_BACKGROUND:
                return new PauseBackgroundFactory();
            case PRINCESS:
                return new PrincessFactory();
            case CHECKPOINT:
                return new CheckpointFactory();
        }

        return new AbstractObjectFactory();
    }

    protected static GameObject produceGameObjectOfType(ObjectType objType, double x, double y, int z, double width, double height) {
        return makeFactory(objType).produceGameObject(x, y, z, width, height);
    }

    public static GameObject generateOfType(ObjectType objType) {
        return produceGameObjectOfType(objType, 0, 0, 0, 1, 1);
    }

    public static GameObject generateOfType(ObjectType objType, int z) {
        return produceGameObjectOfType(objType, 0, 0, z, 1, 1);
    }

    public static GameObject generateOfType(ObjectType objType, double x, double y) {
        return produceGameObjectOfType(objType, x, y, 0, 1, 1);
    }

    public static GameObject generateOfType(ObjectType objType, double x, double y, int z) {
        return produceGameObjectOfType(objType, x, y, z, 1, 1);
    }

    public static GameObject generateOfType(ObjectType objType, double x, double y, double width, double height) {
        return produceGameObjectOfType(objType, x, y, 0, width, height);
    }

    public static GameObject generateOfType(ObjectType objType, double x, double y, int z, double width, double height) {
        return produceGameObjectOfType(objType, x, y, z, width, height);
    }

    public GameObject generate() {
        return produceGameObject(0, 0, 0, 1, 1);
    }

    public GameObject generate(int z) {
        return produceGameObject(0, 0, z, 1, 1);
    }

    public GameObject generate(double x, double y) {
        return produceGameObject(x, y, 0, 1, 1);
    }

    public GameObject generate(double x, double y, int z) {
        return produceGameObject(x, y, z, 1, 1);
    }

    public GameObject generate(double x, double y, double width, double height) {
        return produceGameObject(x, y, 0, width, height);
    }

    public GameObject generate(double x, double y, int z, double width, double height) {
        return produceGameObject(x, y, z, width, height);
    }
}
