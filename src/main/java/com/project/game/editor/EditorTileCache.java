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
        spriteCache.put(6, "assets/hiddenitemblock_tile.png");
        spriteCache.put(7, "assets/princess.png");
        spriteCache.put(8, "assets/cloud.png");
        spriteCache.put(9, "assets/enemy_cloud.png");
        spriteCache.put(10, "assets/enemy.png");
        spriteCache.put(11, "assets/ice.png");
        spriteCache.put(12, "assets/moveplatformR.png");
        spriteCache.put(13, "assets/checkpoint.png");

        disableTransformMutations.put(6, true); // hidden item block
        disableTransformMutations.put(5, true); // hidden spike
        disableTransformMutations.put(3, true); // item block
        //disableTransformMutations.put(4, true);
        disableTransformMutations.put(0, true); // player
        disableTransformMutations.put(7, true); // princess
        disableTransformMutations.put(8, true); // cloud
        disableTransformMutations.put(9, true); // cloud enemy
        disableTransformMutations.put(10, true); // enemy
        disableTransformMutations.put(12, true); // movePlatformR
        disableTransformMutations.put(13, true); // checkpoint
    }
}
