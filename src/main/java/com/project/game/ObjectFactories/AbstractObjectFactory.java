package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;

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
            case CLOUD:
                return new CloudFactory();
            case HIDDEN_BLOCK:
                return new HiddenBlockFactory();
            case HIDDEN_SPIKE:
                return new HiddenSpikeFactory();
            case BACKGROUND:
                return new BackgroundFactory();
            case ESCAPE_KEY:
                return new EscapeKeyDetectorFactory();
            case VOID:
                return new VoidFactory();
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