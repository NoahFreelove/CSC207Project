package com.project.game.editor;

import java.util.HashMap;

public class EditorTileCache {
    public static final int BASE_TILE_SIZE = 64;
    public static final HashMap<Integer, String> spriteCache = new HashMap<>();

    static {
        spriteCache.put(0, "assets/character.png");
        spriteCache.put(1, "assets/ground_brick.png");
    }
}
