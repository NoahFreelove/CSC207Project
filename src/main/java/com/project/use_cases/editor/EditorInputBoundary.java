package com.project.use_cases.editor;

import com.project.interface_adapters.core.editor.EditorTileManager;

import java.util.HashMap;

public interface EditorInputBoundary {
    default HashMap<Integer, Boolean> getTransformMutations() {
        return EditorTileManager.disableTransformMutations;
    }
}
