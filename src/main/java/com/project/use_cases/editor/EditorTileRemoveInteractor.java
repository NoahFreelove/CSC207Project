package com.project.use_cases.editor;

import com.project.entity.core.GameObject;
import com.project.use_cases.core.editor.LevelEditor;

public class EditorTileRemoveInteractor {
    public static void execute(LevelEditor instance, int xPos, int yPos) {
        GameObject foundRef = instance.getTiles().stream().filter(eos -> eos.xPos == xPos && eos.yPos == yPos).findFirst().map(eos -> eos.linkedObject).orElse(null);
        if (foundRef == null)
            return;

        instance.removeSceneObject(foundRef);

        instance.getTiles().removeIf(editorObjectStruct -> editorObjectStruct.linkedObject.equals(foundRef));
    }
}
