package com.project.game;

import java.util.HashMap;

public class TileCache {
    public static final int BASE_TILE_SIZE = 64;
    public static final HashMap<Integer, String> spriteCache = new HashMap<>();

    static {
        spriteCache.put(0, "assets/character.png");
        spriteCache.put(1, "assets/ground_brick.png");
    }
}
