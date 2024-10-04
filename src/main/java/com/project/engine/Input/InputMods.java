package com.project.engine.Input;

/**
 * Represents the different input modifiers that can be used
 * Not gonna bother with bitwise operations for this!
 */
public class InputMods {
    public static final int NONE = 0;
    public static final int SHIFT = 1;
    public static final int CTRL = 2;
    public static final int ALT = 4;

    public static boolean isShift(int mods){
        return (mods & SHIFT) == SHIFT;
    }

    public static boolean isCtrl(int mods){
        return (mods & CTRL) == CTRL;
    }

    public static boolean isAlt(int mods){
        return (mods & ALT) == ALT;
    }

    public static boolean isNone(int mods){
        return mods == NONE;
    }
}
