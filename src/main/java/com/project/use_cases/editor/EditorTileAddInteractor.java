package com.project.use_cases.editor;

import com.project.entity.core.GameObject;
import com.project.use_cases.core.editor.EditorObjectStruct;
import com.project.use_cases.core.editor.LevelEditor;

public class EditorTileAddInteractor {
    public static void execute(LevelEditor instance, int ID, int x, int y, double px, double py, int pr) {
        if (x < 0 || x >= 500 * 64)
            return;

        if (y >= 640 || y<= 0)
            return;

        boolean[] exists = new boolean[1];
        boolean[] playerExists = new boolean[1];
        boolean[] princessExists = new boolean[1];

        instance.getTiles().forEach(editorObjectStruct -> {
            if (editorObjectStruct.xPos == x && editorObjectStruct.yPos == y) {
                exists[0] = true;
            }

            if (editorObjectStruct.ID == 0)
                playerExists[0] = true;
            if (editorObjectStruct.ID == 7)
                princessExists[0] = true;
        });

        if ((ID == 0 && playerExists[0]) || exists[0] || (princessExists[0] && ID == 7)) {
            return;
        }
        GameObject go = new GameObject();
        EditorObjectStruct eos = new EditorObjectStruct(ID, go, x, y, 1, 1, 0);

        EditorInputBoundary boundary = new EditorInputBoundary() {};
        if(!boundary.getTransformMutations().getOrDefault(ID, false)) {
            eos = new EditorObjectStruct(ID, go, x, y, px, py, pr);
        }

        instance.getTiles().add(eos);
        instance.addSceneObject(go);
    }
}
