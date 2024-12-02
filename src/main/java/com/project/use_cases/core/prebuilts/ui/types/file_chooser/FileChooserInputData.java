package com.project.use_cases.core.prebuilts.ui.types.file_chooser;

import java.io.File;

public class FileChooserInputData {
    public static File choose(FileChooserOutputData in) {
        return in.getSelectedFile();
    }
}
