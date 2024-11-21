package com.project.game.editor;

import java.util.HashMap;

public class EditorTileCache {
    public static final int BASE_TILE_SIZE = 64;
    public static final HashMap<Integer, String> spriteCache = new HashMap<>();
    public static final HashMap<Integer, Boolean> disableTransformMutations = new HashMap<>();

    static {
        spriteCache.put(0, "assets/character.png");
        spriteCache.put(1, "assets/ground_brick.png");
        spriteCache.put(2, "assets/brick.png");
        spriteCache.put(3, "assets/item_block.png");
        spriteCache.put(4, "assets/spike.png");
        spriteCache.put(5, "assets/hiddenspike_tile.png");

        disableTransformMutations.put(5, true);
        disableTransformMutations.put(3, true);
        //disableTransformMutations.put(4, true);
        disableTransformMutations.put(0, true);

    }
}
